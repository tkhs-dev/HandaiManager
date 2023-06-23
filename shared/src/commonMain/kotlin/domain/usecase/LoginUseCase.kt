package domain.usecase

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.and
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import domain.repository.CleApiRepository
import domain.repository.IdpRepository

class LoginUseCase(private val idpRepository: IdpRepository,private val cleRepository: CleApiRepository) {
    suspend fun prepareForLogin():Result<LoginStatus,Unit>{
        return cleRepository.getAuthRequest()
            .andThen { idpRepository.prepareForLogin(it) }
            .map {
                when(it){
                    IdpRepository.IdpStatus.NEED_CREDENTIALS -> LoginStatus.NEED_CREDENTIALS
                    IdpRepository.IdpStatus.NEED_OTP -> LoginStatus.NEED_OTP
                    IdpRepository.IdpStatus.SUCCESS -> LoginStatus.SUCCESS
                }
            }
    }

    fun login():Result<LoginStatus,Unit>{
        return Ok(LoginStatus.SUCCESS)
    }

    suspend fun authPassword(userId:String,password:String):Result<LoginStatus,Unit>{
        return idpRepository.authPassword(userId,password)
            .map {
                when(it){
                    IdpRepository.IdpStatus.NEED_CREDENTIALS -> LoginStatus.NEED_CREDENTIALS
                    IdpRepository.IdpStatus.NEED_OTP -> LoginStatus.NEED_OTP
                    IdpRepository.IdpStatus.SUCCESS -> LoginStatus.SUCCESS
                }
            }
            .andThen {
                if(it == LoginStatus.SUCCESS)
                    idpRepository.login()
                        .andThen { cleRepository.login(it)}
                        .map{ LoginStatus.SUCCESS}
                        .mapError { Unit }
                else Ok(it)
            }
    }

    suspend fun authOtp(code:String):Result<LoginStatus,Unit>{
        return idpRepository.authOtp(code)
            .map {
                when(it){
                    IdpRepository.IdpStatus.NEED_CREDENTIALS -> LoginStatus.NEED_CREDENTIALS
                    IdpRepository.IdpStatus.NEED_OTP -> LoginStatus.NEED_OTP
                    IdpRepository.IdpStatus.SUCCESS -> LoginStatus.SUCCESS
                }
            }
    }

    enum class LoginStatus{
        NEED_CREDENTIALS,
        NEED_OTP,
        SUCCESS
    }
}