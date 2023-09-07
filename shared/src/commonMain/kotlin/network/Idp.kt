package network

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import de.jensklingenberg.ktorfit.Ktorfit
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
class Idp(
    private val idpApi: IdpService =
        Ktorfit.Builder().httpClient(HttpClient {
            followRedirects = false
            install(HttpCookies)
        }).baseUrl(BASE_URL)
            .build()
            .create()
) {
    companion object{
        const val BASE_URL = "https://ou-idp.auth.osaka-u.ac.jp/"
    }

    /**
     * 大阪大学のIDPで認証を行う
     * @param authRequestData 認証リクエストデータ
     * @param userid ユーザーID
     * @param password パスワード
     * @param code 二段階認証のコード
     */
    suspend fun authenticate(authRequestData: AuthRequestData, userid: String, password: String, code: String): Result<AuthResult,AuthError> {
        return tryUseCookie(authRequestData).andThen {
                if(it == AuthStatus.NEED_CREDENTIALS){
                    authPassword(userid, password)
                }else{
                    Ok(it)
                }
            }
            .andThen {
                if(it == AuthStatus.NEED_OTP){
                    authOtp(code)
                }else{
                    Ok(it)
                }
            }
            .flatMap { roleSelect() }
    }

    /**
     * クッキーが利用できないか確認
     */
    suspend fun tryUseCookie(authRequestData: AuthRequestData): Result<AuthStatus, AuthError> {
        val res = idpApi.connectSsoSite(authRequestData.samlRequest, authRequestData.relayState, authRequestData.sigAlg, authRequestData.signature)
        return if(res.status.value == 200){
            if(res.bodyAsText().contains("利用者選択")){
                Ok(AuthStatus.SUCCESS)
            }else{
                Ok(AuthStatus.NEED_CREDENTIALS)
            }
        }else{
            Err(AuthError.FAILED)
        }
    }

    /**
     * ユーザーIDとパスワードで認証
     */
    suspend fun authPassword(
        userid: String, password: String
    ): Result<AuthStatus, AuthError> {
        return runCatching { idpApi.authPassword(userid, password) }
            .mapError { AuthError.FAILED }
            .andThen {
                if (it.contains("認証エラー")) {
                    idpApi.refreshAuthPage()
                    Err(AuthError.WRONG_CREDENTIALS)
                } else if (it.contains("MFA")) {
                    Ok(AuthStatus.NEED_OTP)
                } else if (it.contains("利用者選択")) {
                    Ok(AuthStatus.SUCCESS)
                } else {
                    Err(AuthError.FAILED)
                }
            }
    }

    /**
     * 二段階認証を利用して認証
     */
    suspend fun authOtp(code: String): Result<AuthStatus, AuthError> {
        val res = idpApi.authMfa(code)
        return if(res.contains("認証エラー")){
            Err(AuthError.WRONG_OTP_CODE)
        }else if(res.contains("利用者選択")){
            Ok(AuthStatus.SUCCESS)
        }else{
            Err(AuthError.FAILED)
        }
    }

    /**
     * ロール選択画面
     */
    suspend fun roleSelect(): Result<AuthResult,AuthError> {
        val res = idpApi.roleSelect()
        val map = mutableMapOf<String, String>()
        "name=\"([^\"]+)\"\\s*value=\"([^\"]+)\"".toRegex(RegexOption.IGNORE_CASE).findAll(res)
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

    enum class AuthStatus{
        /**
         * ユーザーIDとパスワードの入力が必要
         */
        NEED_CREDENTIALS,

        /**
         * 二段階認証が必要
         */
        NEED_OTP,

        /**
         * 認証成功
         */
        SUCCESS
    }

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