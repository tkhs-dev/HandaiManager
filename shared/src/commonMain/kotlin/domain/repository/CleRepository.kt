package domain.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.toResultOr
import data.cache.CacheManager
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.HttpSendPipeline
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.DateTimePeriod
import model.User
import network.ApiError
import network.AuthRequestData
import network.AuthResponseData
import network.CleService
import util.FileCookiesStorage

class CleRepository(
    private val cacheManager: CacheManager,
    private val fileCookiesStorage: FileCookiesStorage? = null,
    private val cleApi: CleService =
        Ktorfit.Builder().httpClient(HttpClient {
            followRedirects = false
            install(HttpCookies){
                storage = fileCookiesStorage ?: AcceptAllCookiesStorage()
            }
            install(ContentNegotiation){
                json()
            }
        }.also {
            it.sendPipeline.intercept(HttpSendPipeline.State) {
                context.headers[HttpHeaders.Cookie] = context.headers[HttpHeaders.Cookie]?.replace("\"","") ?: ""
            }
        }).baseUrl(CleService.BASE_URL)
            .build()
            .create()
) : ApiRepository(cacheManager), SSOApiRepository {
    override suspend fun getAuthRequest(): Result<AuthRequestData, ApiError> {
        return withContext(Dispatchers.IO) {
            validateHttpResponse(ignoreAuthError = true) {
                cleApi.getSamlRequest()
            }.flatMap {
                it.headers[HttpHeaders.Location]?.let { Url(it).parameters }
                    .toResultOr { ApiError.InvalidResponse(it.status.value, it.bodyAsText()) }
            }.flatMap {
                if (it["SAMLRequest"] == null || it["SigAlg"] == null || it["Signature"] == null) {
                    Err(ApiError.Unknown)
                } else {
                    Ok(
                        AuthRequestData(
                            it["SAMLRequest"]!!,
                            it["RelayState"],
                            it["SigAlg"]!!,
                            it["Signature"]!!
                        )
                    )
                }
            }
        }
    }

    override suspend fun signinWithSso(authResponseData: AuthResponseData): Result<Unit, ApiError> {
        return withContext(Dispatchers.IO) {
            validateHttpResponse {
                cleApi.authSamlSso(
                    authResponseData.samlResponse,
                    authResponseData.relayState ?: "null"
                )
            }.flatMap {
                if (it.status == HttpStatusCode.Found) {
                    Ok(Unit)
                } else {
                    Err(ApiError.InvalidResponse(it.status.value, it.bodyAsText()))
                }
            }
        }
    }

    suspend fun getUserInfo(): Result<User,ApiError> {
        return safeApiCall {
            useCache<User>("cle_user_info", User.serializer(), ignoreExpired = true) {
                setAge(DateTimePeriod(months = 6))
                withContext(Dispatchers.IO) {
                    cleApi.getUserInfo()
                }
            }
        }
    }
}