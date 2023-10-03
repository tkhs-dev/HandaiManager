package network

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.statement.HttpResponse

/**
 * Ktorfit用のインターフェース
 */
interface IdpService{
    companion object{
        const val BASE_URL = "https://ou-idp.auth.osaka-u.ac.jp/"
    }
    @GET("idp/sso_redirect")
    suspend fun connectSsoSite(
        @Query("SAMLRequest") samlRequest: String,
        @Query("RelayState") relayState: String?,
        @Query("SigAlg") sigAlg: String,
        @Query("Signature") signature: String,
    ): HttpResponse

    @GET("idp/idplogin")
    suspend fun refreshAuthPage()

    @POST("idp/authnPwd")
    suspend fun authPassword(
        @Query("USER_ID") userid: String,
        @Query("USER_PASSWORD") password: String,
        @Query("CHECK_BOX") checkbox: String = "",
        @Query("IDButton") submit: String = "ログイン"
    ): String

    @POST("idp/otpAuth")
    suspend fun authMfa(
        @Query("OTP_CODE") otp: String,
        @Query("STORE_OTP_AUTH_RESULT") submit: String = "1"
    ): String

    @POST("idp/roleselect?role=self_0")
    suspend fun roleSelect(): String
}

data class AuthResponseData(
    val samlResponse: String, val relayState: String?
)

data class AuthRequestData(
    val samlRequest: String, val relayState: String?, val sigAlg: String, val signature: String
)