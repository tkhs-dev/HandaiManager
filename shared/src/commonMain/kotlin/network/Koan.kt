package network

import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.statement.HttpResponse

interface KoanService{
    companion object {
        const val BASE_URL = "https://www.koan.osaka-u.ac.jp/"
    }

    @GET("campusweb/ssologin.do?page=portal")
    suspend fun getSamlRequest(): HttpResponse

    @POST("Shibboleth.sso/SAML2/POST")
    @FormUrlEncoded
    suspend fun authSamlSso(@Field("SAMLResponse") samlResponse:String, @Field("RelayState") relayState:String?, @Field("button") button:String = "Send"): HttpResponse

}