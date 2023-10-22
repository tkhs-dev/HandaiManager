package domain.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.toResultOr
import domain.repository.CleRepository
import domain.repository.CredentialRepository
import domain.repository.IdpRepository
import domain.repository.KoanRepository
import domain.repository.SSOApiRepository
import kotlinx.datetime.Clock
import model.Credential
import network.ApiError
import util.Logger
import util.TotpUtil

class LoginUseCase(
    private val idpRepository: IdpRepository,
    private val cleRepository: CleRepository,
    private val koanRepository: KoanRepository,
    private val credentialRepository: CredentialRepository
) {
    private suspend fun prepareForLogin(apiRepo:SSOApiRepository): Result<LoginStatus, Unit>{
        return apiRepo.getAuthRequest()
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

    suspend fun prepareForLoginCle(): Result<LoginStatus, Unit> {
        return prepareForLogin(cleRepository)
    }

    suspend fun prepareForLoginKoan(): Result<LoginStatus, Unit> {
        return prepareForLogin(koanRepository)
    }

    suspend fun loginCleWithSavedCredential(): Result<Unit, Unit> {
        return credentialRepository.loadCredential()
            .toResultOr {  }
            .flatMap {
                loginCle(it)
            }
    }

    suspend fun loginKoanWithSavedCredential(): Result<Unit, Unit> {
        return credentialRepository.loadCredential()
            .toResultOr {  }
            .flatMap {
                loginKoan(it)
            }
    }

    private suspend fun login(apiRepo: SSOApiRepository, loginResult: Result<LoginStatus,Unit>, credential: Credential):Result<Unit,Unit> {
        return loginResult.flatMap {
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
                idpRepository.roleSelect()
                    .andThen { apiRepo.signinWithSso(it) }
                    .mapError {  }
            else Err(Unit)
        }
    }
    suspend fun loginCle(credential: Credential): Result<Unit, Unit> {
        return login(cleRepository, prepareForLoginCle(),credential)
    }

    suspend fun loginKoan(credential: Credential): Result<Unit, Unit> {
        return koanRepository.getAuthRequest()
            .onFailure {
                if(it is ApiError.InvalidResponse && it.message?.equals("loggedin") == true) {
                    return Ok(Unit)
                }
            }
            .flatMap {
                login(koanRepository, prepareForLoginKoan(),credential)
            }.mapError {  }
    }

    suspend fun authPassword(userId: String, password: String): Result<LoginStatus, Unit> {
        return idpRepository.authPassword(userId, password)
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