package domain.repository

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.mapOr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import network.Cle
import network.Idp

class IdpRepository(private val idpApi: Idp){
    suspend fun prepareForLogin(authRequestData: Idp.AuthRequestData):Result<IdpStatus,Unit>{
        return withContext(Dispatchers.IO){
                idpApi.tryUseCookie(authRequestData)
                .andThen {
                    if(it == Idp.AuthStatus.NEED_CREDENTIALS){
                        Ok(IdpStatus.NEED_CREDENTIALS)
                    }else{
                        Ok(IdpStatus.SUCCESS)
                    }
                }
                .mapError { Unit }
        }
    }

    suspend fun authPassword(userId:String,password:String):Result<IdpStatus,Unit>{
        return withContext(Dispatchers.IO){
            idpApi.authPassword(userId,password)
                .andThen {
                    if(it == Idp.AuthStatus.NEED_OTP){
                        Ok(IdpStatus.NEED_OTP)
                    }else{
                        Ok(IdpStatus.SUCCESS)
                    }
                }
                .mapError { Unit }
        }
    }

    suspend fun authOtp(code:String):Result<IdpStatus,Unit>{
        return withContext(Dispatchers.IO){
            idpApi.authOtp(code)
                .andThen {
                    if(it == Idp.AuthStatus.SUCCESS){
                        Ok(IdpStatus.SUCCESS)
                    }else{
                        Ok(IdpStatus.NEED_OTP)
                    }
                }
                .mapError { Unit }
        }
    }

    suspend fun login():Result<Idp.AuthResult,Unit>{
        return withContext(Dispatchers.IO){
            idpApi.roleSelect()
                .mapError { Unit }
        }
    }

    enum class IdpStatus{
        NEED_CREDENTIALS,
        NEED_OTP,
        SUCCESS
    }
}