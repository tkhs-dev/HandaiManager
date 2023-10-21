package network

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

    @GET("campusweb/campusportal.do?page=main&tabId=kh")
    suspend fun getSchedulePage(): HttpResponse

    @GET("campusweb/campussquare.do?_flowId=KHW0001100-flow")
    suspend fun getWeeklyScheduleTable(): HttpResponse

    @POST("campusweb/campussquare.do")
    @FormUrlEncoded
    suspend fun getScheduleList(@Field("_flowExecutionKey") flowExecutionKey:String, @Field("dispType") dispType:String = "list", @Field("dispData") dispData:String = "all", @Field("_eventId_search") eventId_search:String = " 表 示 す る", @Field("startDay") startDay:String, @Field("startDay_year") startDay_year:Int, @Field("startDay_month") startDay_month:Int, @Field("startDay_day") startDay_day:Int, @Field("endDay") endDay:String, @Field("endDay_year") endDay_year: Int, @Field("endDay_month") endDay_month: Int, @Field("endDay_day") endDay_day: Int, @Field("rishuchuFlg") rishuchuFlg:Boolean, @Field("_rishuchuFlg") _rishuchuFlg:Int, @Field("kyokanCode") kyokanCode:String, @Field("shozokuCode") shozokuCode:String): HttpResponse

    @GET("campusweb/campussquare.do?_flowId=RSW0001000-flow")
    suspend fun getTimeTable(): HttpResponse

    /**
     * this function should be called after getTimeTable()
     */
    @GET("campusweb/campussquare.do?_eventId=search&gakkiKbnCode=4")
    suspend fun getTimeTableByTerm(@Query("_flowExecutionKey") flowExecutionKey:String, @Query("gakkiKbnCode") gakkiKbnCode:Int ): HttpResponse
}