package network
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.toResultOr
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.statement.request
import io.ktor.http.Cookie
import io.ktor.http.setCookie
import util.Logger

class Cle(
    val cleApi: CleService =
        Ktorfit.Builder().httpClient(HttpClient {
            followRedirects = false
            install(HttpCookies)
        }).baseUrl(BASE_URL)
            .build()
            .create<CleService>()
){
    companion object {
        const val BASE_URL = "https://www.cle.osaka-u.ac.jp/"
    }
    suspend fun getAuthRequestData():Result<Idp.AuthRequestData,ApiError>{
        return cleApi.getSamlRequest()
            .toResultOr { ApiError.UNKNOWN }
            .flatMap { it.headers[HttpHeaders.Location]?.let { Url(it).parameters }.toResultOr { ApiError.INVALID_RESPONSE } }
            .flatMap {
                if(it["SAMLRequest"] == null || it["SigAlg"] == null || it["Signature"] == null) {
                    Err(ApiError.INVALID_RESPONSE)
                }else{
                    Ok(Idp.AuthRequestData(
                        it["SAMLRequest"]!!,
                        it["RelayState"],
                        it["SigAlg"]!!,
                        it["Signature"]!!
                    ) )
                }
            }
    }

    suspend fun signinWithSso(authResult: Idp.AuthResult): Result<Cookie,Unit>{
        val res = cleApi.authSamlSso(authResult.samlResponse,authResult.relayState?:"null")
        return if(res.headers.contains("location")){
            val c = res.request.setCookie()
            Logger.error("TEST",c.joinToString { it.name })
            Ok(res.setCookie().first { it.name == "BbRouter" })
        }else{
            Err(Unit)
        }
    }

    enum class ApiError{
        INVALID_PARAMETER,
        INVALID_RESPONSE,
        UNKNOWN
    }
}

interface CleService{
    @GET("auth-saml/saml/login?apId=_3936_1&redirectUrl=https://www.cle.osaka-u.ac.jp/ultra")
    suspend fun getSamlRequest(): HttpResponse

    @POST("auth-saml/saml/SSO")
    @FormUrlEncoded
    suspend fun authSamlSso(@Field("SAMLResponse") samlResponse:String,@Field("RelayState") relayState:String?,@Field("button") button:String = "Send",): HttpResponse
}