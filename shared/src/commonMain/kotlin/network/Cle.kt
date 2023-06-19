package network
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.HttpHeaders
import io.ktor.http.Url

object Cle{
    val ktorfit = Ktorfit.Builder().httpClient(HttpClient(){
        followRedirects = false
        install(HttpCookies)
    }).baseUrl("https://www.cle.osaka-u.ac.jp/").build()
    val cleApi = ktorfit.create<CleService>()

    suspend fun getAuthRequestData():Idp.AuthRequestData?{
        val response = cleApi.getHomePage()
        val params = Url(response.headers[HttpHeaders.Location] ?: return null).parameters
        return Idp.AuthRequestData(
            params["SAMLRequest"] ?: return null,
            params["RelayState"],
            params["SigAlg"] ?: return null,
            params["Signature"] ?: return null
        )
    }
}

interface CleService{
    @GET("/auth-saml/saml/login?apId=_3936_1&redirectUrl=https://www.cle.osaka-u.ac.jp/ultra")
    suspend fun getHomePage(): HttpResponse
}