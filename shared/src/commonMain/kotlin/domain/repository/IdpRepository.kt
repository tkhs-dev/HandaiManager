package domain.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.runCatching
import data.cache.CacheManager
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import data.network.ApiError
import data.network.AuthRequestData
import data.network.AuthResponseData
import data.network.IdpService
import util.FileCookiesStorage
import util.Logger

class IdpRepository(
    private val cacheManager: CacheManager,
    private val fileCookiesStorage: FileCookiesStorage? = null,
    private val idpApi: IdpService =
        Ktorfit.Builder().httpClient(HttpClient {
            followRedirects = false
            install(HttpCookies){
                storage = fileCookiesStorage ?: AcceptAllCookiesStorage()
            }
        }).baseUrl(IdpService.BASE_URL)
            .build()
            .create()
):ApiRepository(cacheManager) {
    suspend fun authenticate(authRequestData: AuthRequestData, userid: String, password: String, code: String): Result<AuthResponseData, ApiError> {
        return prepareForLogin(authRequestData)
            .andThen {
                if(it == IdpStatus.NEED_CREDENTIALS){
                    authPassword(userid, password)
                }else{
                    Ok(it)
                }
            }
            .andThen {
                if(it == IdpStatus.NEED_OTP){
                    authOtp(code)
                }else{
                    Ok(it)
                }
            }
            .flatMap { roleSelect() }
    }

    suspend fun prepareForLogin(authRequestData: AuthRequestData):Result<IdpStatus, ApiError>{
        return withContext(Dispatchers.IO){
            validateHttpResponse(ignoreAuthError = true) {
                idpApi.connectSsoSite(authRequestData.samlRequest, authRequestData.relayState, authRequestData.sigAlg, authRequestData.signature)
            }
                .flatMap{
                    Logger.debug(this::class.simpleName, it.request.headers)
                    if(it.status.value == 200) {
                        if(it.bodyAsText().contains("利用者選択")){
                            Logger.debug(this::class.simpleName, "Pass idp auth with saved cookies")
                            Ok(IdpStatus.SUCCESS)
                        }else{
                            Ok(IdpStatus.NEED_CREDENTIALS)
                        }
                    }else{
                        Err(ApiError.InvalidResponse(it.status.value, it.bodyAsText()))
                    }
                }
        }
    }

    suspend fun authPassword(userId:String,password:String):Result<IdpStatus, ApiError>{
        return withContext(Dispatchers.IO){
            runCatching {
                idpApi.authPassword(userId,password)
            }.onFailure {
                Logger.error(this::class.simpleName, it)
            }.mapError {
                ApiError.InternalException(it)
            }.andThen {
                if (it.contains("認証エラー")) {
                    idpApi.refreshAuthPage()
                    Err(ApiError.WrongCredentials)
                } else if (it.contains("MFA")) {
                    Ok(IdpStatus.NEED_OTP)
                } else if (it.contains("利用者選択")) {
                    Ok(IdpStatus.SUCCESS)
                } else {
                    Err(ApiError.InvalidResponse(200, it))
                }
            }
        }
    }

    suspend fun authOtp(code:String):Result<IdpStatus, ApiError>{
        return withContext(Dispatchers.IO){
            runCatching{
                idpApi.authMfa(code)
            }.onFailure {
                Logger.error(this::class.simpleName, it)
            }.mapError {
                ApiError.InternalException(it)
            }.andThen {
                if(it.contains("認証エラー")){
                    Err(ApiError.WrongCredentials)
                }else if(it.contains("利用者選択")){
                    Ok(IdpStatus.SUCCESS)
                }else{
                    Err(ApiError.InvalidResponse(200, it))
                }
            }
        }
    }

    suspend fun roleSelect():Result<AuthResponseData, ApiError>{
        return withContext(Dispatchers.IO){
            runCatching {
                idpApi.roleSelect()
            }.onFailure {
                Logger.error(this::class.simpleName, it)
            }.mapError {
                ApiError.InternalException(it)
            }.map {
                val map = mutableMapOf<String, String>()
                "name=\"([^\"]+)\"\\s*value=\"([^\"]+)\"".toRegex(RegexOption.IGNORE_CASE).findAll(it)
                    .forEach {
                            matchResult->
                        val (name, value) = matchResult.destructured
                        map[name] = value
                    }
                map
            }.flatMap {
                val samlResponse = it["SAMLResponse"] ?: return@flatMap Err(ApiError.InvalidResponse(200, "SAMLResponse not found"))
                val relayState = it["RelayState"] ?: "null"
                Logger.info(this::class.simpleName, "OU idp login successful")
                Ok(AuthResponseData(samlResponse,relayState))
            }
        }
    }

    enum class IdpStatus{
        NEED_CREDENTIALS,
        NEED_OTP,
        SUCCESS
    }
}