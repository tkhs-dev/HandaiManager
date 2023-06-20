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
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.statement.bodyAsText

object Cle{
    val ktorfit = Ktorfit.Builder().httpClient(HttpClient(){
        followRedirects = false
        install(HttpCookies)
    }).baseUrl("https://www.cle.osaka-u.ac.jp/").build()
    val cleApi = ktorfit.create<CleService>()

    suspend fun getAuthRequestData():Idp.AuthRequestData?{
        val response = cleApi.getSamlRequest()
        val params = Url(response.headers[HttpHeaders.Location] ?: return null).parameters
        return Idp.AuthRequestData(
            params["SAMLRequest"] ?: return null,
            params["RelayState"],
            params["SigAlg"] ?: return null,
            params["Signature"] ?: return null
        )
    }

    suspend fun signinWithSso(authResult: Idp.AuthResult): Result<Unit,Unit>{
        val res = cleApi.authSamlSso(authResult.samlResponse,authResult.relayState?:"null")
        return if(res.headers.contains("location")){
            Ok(Unit)
        }else{
            Err(Unit)
        }
    }
}

interface CleService{
    @GET("auth-saml/saml/login?apId=_3936_1&redirectUrl=https://www.cle.osaka-u.ac.jp/ultra")
    suspend fun getSamlRequest(): HttpResponse

    @POST("auth-saml/saml/SSO")
    @FormUrlEncoded
    suspend fun authSamlSso(@Field("SAMLResponse") samlResponse:String,@Field("RelayState") relayState:String?,@Field("button") button:String = "Send",): HttpResponse
}