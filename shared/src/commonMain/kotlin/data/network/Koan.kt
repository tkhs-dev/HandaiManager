package data.network

import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.statement.HttpResponse

interface KoanService{
    companion object {
        const val BASE_URL = "https://koan.osaka-u.ac.jp/"
    }

    @GET("campusweb/ssologin.do?page=portal")
    suspend fun getSamlRequest(): HttpResponse

    @POST("Shibboleth.sso/SAML2/POST")
    @FormUrlEncoded
    suspend fun authSamlSso(@Field("SAMLResponse") samlResponse:String, @Field("RelayState") relayState:String?, @Field("button") button:String = "Send"): HttpResponse

    @GET("campusweb/campusportal.do?page=main&tabId=rs")
    suspend fun getRishuPage(): HttpResponse

    @GET("campusweb/campussquare.do?_flowId=RSW0001000-flow")
    suspend fun getTimeTable(): HttpResponse

    /**
     * this function should be called after getTimeTable()
     */
    @GET("campusweb/campussquare.do?_eventId=search&gakkiKbnCode=4")
    suspend fun getTimeTableByTerm(@Query("_flowExecutionKey") flowExecutionKey:String, @Query("gakkiKbnCode") gakkiKbnCode:Int ): HttpResponse
}