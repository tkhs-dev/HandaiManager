package domain.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.toResultOr
import domain.repository.CleApiRepository
import domain.repository.CredentialRepository
import domain.repository.IdpRepository
import model.Credential
import kotlinx.datetime.Clock
import util.Logger
import util.TotpUtil

class LoginUseCase(
    private val idpRepository: IdpRepository,
    private val cleRepository: CleApiRepository,
    private val credentialRepository: CredentialRepository
) {
    suspend fun prepareForLogin(): Result<LoginStatus, Unit> {
        return cleRepository.getAuthRequest()
            .andThen { idpRepository.prepareForLogin(it) }
            .map {
                when (it) {
                    IdpRepository.IdpStatus.NEED_CREDENTIALS -> LoginStatus.NEED_CREDENTIALS
                    IdpRepository.IdpStatus.NEED_OTP -> LoginStatus.NEED_MFA
                    IdpRepository.IdpStatus.SUCCESS -> LoginStatus.SUCCESS
                }
            }.mapError {
                Logger.error(this::class.simpleName, it)
            }
    }

    suspend fun loginWithSavedCredential(): Result<Unit, Unit> {
        return credentialRepository.loadCredential()
            .toResultOr {  }
            .flatMap {
                login(it)
            }
    }

    suspend fun login(credential: Credential): Result<Unit, Unit> {
        return prepareForLogin()
            .flatMap {
                when (it) {
                    LoginStatus.NEED_CREDENTIALS -> authPassword(credential.userId, credential.password)
                    LoginStatus.NEED_MFA -> credential.otpSecret?.let { authWithOtpSecret(it) }?: Err(Unit)
                    LoginStatus.SUCCESS -> Ok(it)
                }
            }.flatMap {
                when (it) {
                    LoginStatus.NEED_CREDENTIALS -> authPassword(credential.userId, credential.password)
                    LoginStatus.NEED_MFA -> credential.otpSecret?.let { authWithOtpSecret(it) }?: Err(Unit)
                    LoginStatus.SUCCESS -> Ok(it)
                }
            }.flatMap {
                if(it == LoginStatus.SUCCESS)
                    Ok(Unit)
                else Err(Unit)
            }
    }

    suspend fun authPassword(userId: String, password: String): Result<LoginStatus, Unit> {
        return idpRepository.authPassword(userId, password)
            .map {
                when (it) {
                    IdpRepository.IdpStatus.NEED_CREDENTIALS -> LoginStatus.NEED_CREDENTIALS
                    IdpRepository.IdpStatus.NEED_OTP -> LoginStatus.NEED_MFA
                    IdpRepository.IdpStatus.SUCCESS -> LoginStatus.SUCCESS
                }
            }
            .andThen {
                if (it == LoginStatus.SUCCESS)
                    idpRepository.roleSelect()
                        .andThen { cleRepository.signinWithSso(it) }
                        .map { LoginStatus.SUCCESS }
                else Ok(it)
            }.mapError {
                Logger.error(this::class.simpleName, it)
            }
    }

    suspend fun authWithOtpSecret(secret: String):Result<LoginStatus, Unit>{
        return authOtp(TotpUtil.generateTotpCode(secret, Clock.System.now().epochSeconds))
    }

    suspend fun authOtp(code: String): Result<LoginStatus, Unit> {
        return idpRepository.authOtp(code)
            .map {
                when (it) {
                    IdpRepository.IdpStatus.NEED_CREDENTIALS -> LoginStatus.NEED_CREDENTIALS
                    IdpRepository.IdpStatus.NEED_OTP -> LoginStatus.NEED_MFA
                    IdpRepository.IdpStatus.SUCCESS -> LoginStatus.SUCCESS
                }
            }.andThen {
                if (it == LoginStatus.SUCCESS)
                    idpRepository.roleSelect()
                        .andThen { cleRepository.signinWithSso(it) }
                        .map { LoginStatus.SUCCESS }
                else Ok(it)
            }.mapError { }
    }

    fun saveCredential(credential: Credential) {
        credentialRepository.saveCredential(credential)
    }

    enum class LoginStatus {
        NEED_CREDENTIALS,
        NEED_MFA,
        SUCCESS
    }
}