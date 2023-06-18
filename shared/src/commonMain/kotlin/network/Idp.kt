package network

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.getError
import com.github.michaelbull.result.getOr
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapEither
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.orElse

/**
 * 大阪大学のIDPでの認証を取り扱うクラス
 */
object Idp {
    const val AUTH_PWD_URI = "https://ou-idp.auth.osaka-u.ac.jp/idp/authnPwd"
    const val AUTH_OTP_URI = "https://ou-idp.auth.osaka-u.ac.jp/idp/otpAuth"
    const val ROLE_SELECT_URI = "https://ou-idp.auth.osaka-u.ac.jp/idp/roleselect"

    /**
     * 大阪大学のIDPで認証を行う
     * @param redirect 認証後にリダイレクトするURI
     * @param userid ユーザーID
     * @param password パスワード
     * @param code 二段階認証のコード
     */
    fun authenticate(redirectUrl: String, userid: String, password: String, code: String): AuthResult {
        return useCookie().orElse {
            if(it == AuthError.NEED_CREDENTIALS){
                authPassword(redirectUrl, userid, password)
            }else{
                return AuthResult.ERROR
            }
        }
            .orElse {
                if(it == AuthError.NEED_OTP){
                    authOtp(code)
                }else if(it == AuthError.WRONG_CREDENTIALS){
                    return AuthResult(AuthStatus.WRONG_CREDENTIALS, null, null)
                }else{
                    return AuthResult.ERROR
                }
            }
            .mapError {
                if(it == AuthError.WRONG_OTP_CODE){
                    return AuthResult(AuthStatus.WRONG_OTP_CODE, null, null)
                }else if(it == AuthError.FAILED){
                    return AuthResult.ERROR
                }
            }
            .flatMap { roleSelect() }
            .getOr(AuthResult.ERROR)
    }

    /**
     * クッキーが利用できないか確認
     */
    private fun useCookie(): Result<Unit, AuthError> {
        return Err(AuthError.NEED_CREDENTIALS)
    }

    /**
     * ユーザーIDとパスワードで認証
     */
    private fun authPassword(
        redirectUrl: String, userid: String, password: String
    ): Result<Unit, AuthError> {
        print("Auth password")
        return Err(AuthError.NEED_OTP)
    }

    /**
     * 二段階認証を利用して認証
     */
    private fun authOtp(code: String): Result<Unit, AuthError> {
        print("Auth OTP")
        return Ok(Unit)
    }

    /**
     * ロール選択画面
     */
    private fun roleSelect(): Result<AuthResult,Unit> {
        print("Role select")
        return Ok(AuthResult(AuthStatus.SUCCESS, null, null))
    }

    data class AuthResult(
        val status: AuthStatus, val samlResponse: String?, val relayState: String?
    ){
        companion object{
            val ERROR:AuthResult = AuthResult(AuthStatus.ERROR, null, null)
        }
    }

    enum class AuthStatus {
        SUCCESS, WRONG_CREDENTIALS, WRONG_OTP_CODE, ERROR
    }

    private enum class AuthError {
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
         * ログインに失敗した
         */
        FAILED
    }
}