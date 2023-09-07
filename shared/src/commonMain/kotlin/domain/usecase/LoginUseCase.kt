package domain.usecase

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import domain.repository.CleApiRepository
import domain.repository.CredentialRepository
import domain.repository.IdpRepository
import entities.Credential
import util.Logger

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
                    IdpRepository.IdpStatus.NEED_OTP -> LoginStatus.NEED_OTP
                    IdpRepository.IdpStatus.SUCCESS -> LoginStatus.SUCCESS
                }
            }
    }

    fun login(): Result<LoginStatus, Unit> {
        return Ok(LoginStatus.SUCCESS)
    }

    suspend fun authPassword(userId: String, password: String): Result<LoginStatus, Unit> {
        return idpRepository.authPassword(userId, password)
            .map {
                when (it) {
                    IdpRepository.IdpStatus.NEED_CREDENTIALS -> LoginStatus.NEED_CREDENTIALS
                    IdpRepository.IdpStatus.NEED_OTP -> LoginStatus.NEED_OTP
                    IdpRepository.IdpStatus.SUCCESS -> LoginStatus.SUCCESS
                }
            }
            .andThen {
                if (it == LoginStatus.SUCCESS)
                    idpRepository.login()
                        .andThen { cleRepository.login(it) }
                        .map { LoginStatus.SUCCESS }
                        .mapError { }
                else Ok(it)
            }
    }

    suspend fun authOtp(code: String): Result<LoginStatus, Unit> {
        return idpRepository.authOtp(code)
            .map {
                when (it) {
                    IdpRepository.IdpStatus.NEED_CREDENTIALS -> LoginStatus.NEED_CREDENTIALS
                    IdpRepository.IdpStatus.NEED_OTP -> LoginStatus.NEED_OTP
                    IdpRepository.IdpStatus.SUCCESS -> LoginStatus.SUCCESS
                }
            }
    }

    fun saveCredential(credential: Credential) {
        credentialRepository.saveCredential(credential)
    }

    enum class LoginStatus {
        NEED_CREDENTIALS,
        NEED_OTP,
        SUCCESS
    }
}