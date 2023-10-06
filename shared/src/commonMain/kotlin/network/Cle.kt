package network
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.statement.HttpResponse
import model.User

interface CleService{
    companion object {
        const val BASE_URL = "https://www.cle.osaka-u.ac.jp/"
    }

    @GET("auth-saml/saml/login?apId=_3936_1&redirectUrl=https://www.cle.osaka-u.ac.jp/ultra")
    suspend fun getSamlRequest(): HttpResponse

    @POST("auth-saml/saml/SSO")
    @FormUrlEncoded
    suspend fun authSamlSso(@Field("SAMLResponse") samlResponse:String,@Field("RelayState") relayState:String?,@Field("button") button:String = "Send"): HttpResponse

    @GET("learn/api/public/v1/users/me")
    suspend fun getUserInfo():User
}