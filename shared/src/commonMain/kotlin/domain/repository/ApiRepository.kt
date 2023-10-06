package domain.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.runCatching
import com.github.michaelbull.result.toResultOr
import data.cache.CacheManager
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.serialization.KSerializer
import network.ApiError
import util.Logger
import kotlin.jvm.JvmName
import kotlin.time.Duration

abstract class ApiRepository(private val cacheManager: CacheManager) {

    protected inline fun <T : Any> safeApiCall(
        onCatchException: (Throwable) -> Unit = {},
        block: () -> T
    ): Result<T, ApiError> {
        return runCatching {
            block()
        }.onFailure {
            Logger.error(this::class.simpleName, it)
            onCatchException(it)
        }.mapError {
            if(it is ConnectTimeoutException || it is UnresolvedAddressException){
                ApiError.NetworkError
            }else{
                ApiError.InternalException(it)
            }
        }
    }
    protected suspend inline fun validateHttpResponse(
        ignoreAuthError:Boolean = false,
        onCatchException: (Throwable) -> Unit = {},
        onResponseFailure: (HttpResponse) -> Unit = {},
        block: () -> HttpResponse
    ): Result<HttpResponse, ApiError> {
        return safeApiCall(onCatchException) {
            block()
        }.flatMap {
            if (it.status.isSuccess() || it.status== HttpStatusCode.Found) {
                if(it.headers["Location"]?.contains("sso_redirect") == true && !ignoreAuthError ){
                    Err(ApiError.AuthError)
                }else{
                    Ok(it)
                }
            } else {
                onResponseFailure(it)
                if(it.status == HttpStatusCode.Unauthorized){
                    Err(ApiError.AuthError)
                } else{
                    Err(ApiError.InvalidResponse(it.status.value, it.bodyAsText()))
                }
            }
        }
    }

    suspend fun <T : Any> useCache(
        key:String,
        serializer: KSerializer<T>,
        ignoreExpired:Boolean = false,
        block: suspend CacheConfig.() -> T
    ):T{
        val cacheConfig = CacheConfig()
        return cacheManager.get<T>(key, serializer, ignoreExpired) ?: block(cacheConfig).also {
            cacheManager.set(key, serializer, it, cacheConfig.expire)
        }
    }

    @JvmName("useCacheWithResult")
    suspend fun <T,U> useCache(
        key:String,
        serializer: KSerializer<T>,
        ignoreExpired:Boolean = false,
        block: suspend CacheConfig.() -> Result<T,U>
    ):Result<T,U>{
        val cacheConfig = CacheConfig()
        return cacheManager.get<T>(key, serializer, ignoreExpired)
            .toResultOr {}
            .mapBoth(
                success = { Ok(it) },
                failure = {
                    block(cacheConfig).also {
                        it.onSuccess {
                            cacheManager.set(key, serializer, it, cacheConfig.expire)
                        }
                    }
                }
            )
    }

    class CacheConfig(){
        internal var expire:Long = 0

        fun setExpire(epochMillis:Long){
            this.expire = epochMillis
        }

        fun setAge(duration: Duration){
            this.expire = Clock.System.now().plus(duration).toEpochMilliseconds()
        }

        fun setAge(period: DateTimePeriod){
            this.expire = Clock.System.now().plus(period,TimeZone.currentSystemDefault()).toEpochMilliseconds()
        }
    }
}