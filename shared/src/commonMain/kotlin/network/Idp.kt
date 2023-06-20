package network

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.getOr
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.orElse
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText

/**
 * 大阪大学のIDPでの認証を取り扱うクラス
 */
object Idp {
    val ktorfit = Ktorfit.Builder().httpClient(HttpClient(){
        followRedirects = false
        install(HttpCookies)
    }).baseUrl("https://ou-idp.auth.osaka-u.ac.jp/").build()
    val idpApi = ktorfit.create<IdpService>()

    /**
     * 大阪大学のIDPで認証を行う
     * @param redirect 認証後にリダイレクトするURI
     * @param userid ユーザーID
     * @param password パスワード
     * @param code 二段階認証のコード
     */
    suspend fun authenticate(authRequestData: AuthRequestData, userid: String, password: String, code: String): Result<AuthResult,AuthError> {
        return tryUseCookie(authRequestData).orElse {
                if(it == AuthError.NEED_CREDENTIALS){
                    authPassword(userid, password)
                }else{
                    Err(it)
                }
            }
            .orElse {
                if(it == AuthError.NEED_OTP){
                    authOtp(code)
                }else{
                    Err(it)
                }
            }
            .flatMap { roleSelect() }
    }

    /**
     * クッキーが利用できないか確認
     */
    private suspend fun tryUseCookie(authRequestData: AuthRequestData): Result<Unit, AuthError> {
        val res = idpApi.connectSsoSite(authRequestData.samlRequest, authRequestData.relayState, authRequestData.sigAlg, authRequestData.signature)
        if(res.status.value == 200){
            if(res.bodyAsText().contains("利用者選択")){
                return Ok(Unit)
            }else{
                return Err(AuthError.NEED_CREDENTIALS)
            }
        }else{
            return Err(AuthError.FAILED)
        }
    }

    /**
     * ユーザーIDとパスワードで認証
     */
    private suspend fun authPassword(
        userid: String, password: String
    ): Result<Unit, AuthError> {
        val res = idpApi.authPassword(userid, password)
        if(res.contains("認証エラー")){
            return Err(AuthError.WRONG_CREDENTIALS)
        }else if(res.contains("MFA")){
            return Err(AuthError.NEED_OTP)
        }else{
            return Ok(Unit)
        }
    }

    /**
     * 二段階認証を利用して認証
     */
    private suspend fun authOtp(code: String): Result<Unit, AuthError> {
        val res = idpApi.authMfa(code);
        if(res.contains("認証エラー")){
            return Err(AuthError.WRONG_OTP_CODE)
        }else{
            return Ok(Unit)
        }
    }

    /**
     * ロール選択画面
     */
    private suspend fun roleSelect(): Result<AuthResult,AuthError> {
        val res = idpApi.roleSelect()
        val map = mutableMapOf<String, String>()
        val l = "name=\"([^\"]+)\"\\s*value=\"([^\"]+)\"".toRegex(RegexOption.IGNORE_CASE).findAll(res)
            .forEach {
                matchResult->
                val (name, value) = matchResult.destructured
                map[name] = value
            }

        return Ok(AuthResult(map["SAMLResponse"]?:return Err(AuthError.FAILED), if(map["RelayState"] == "null") null else map["RelayState"]))
    }

    data class AuthResult(
        val samlResponse: String, val relayState: String?
    )

    data class AuthRequestData(
        val samlRequest: String, val relayState: String?, val sigAlg: String, val signature: String
    )

    enum class AuthError {
        /**
         * ユーザーIDまたはパスワードが間違っている
         */
        WRONG_CREDENTIALS,

        /**
         * 二段階認証のコードが間違っている
         */
        WRONG_OTP_CODE,

        /**
         * ユーザーIDとパスワードの入力が必要
         */
        NEED_CREDENTIALS,

        /**
         * 二段階認証が必要
         */
        NEED_OTP,

        /**
         * レスポンスが不正
         */
        INVALID_RESPONSE,

        /**
         * ログインに失敗した
         */
        FAILED
    }
}

/**
 * Ktorfit用のインターフェース
 */
interface IdpService{
    @GET("idp/sso_redirect")
    suspend fun connectSsoSite(
        @Query("SAMLRequest") samlRequest: String,
        @Query("RelayState") relayState: String?,
        @Query("SigAlg") sigAlg: String,
        @Query("Signature") signature: String,
    ): HttpResponse

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