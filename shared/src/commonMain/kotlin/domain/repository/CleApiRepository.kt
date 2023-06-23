package domain.repository

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import network.Cle
import network.Idp

class CleApiRepository(private val cle: Cle) {
    suspend fun getAuthRequest():Result<Idp.AuthRequestData,Unit>{
        return cle.getAuthRequestData()
            .mapError { Unit }
    }

    suspend fun login(authResult: Idp.AuthResult):Result<Unit,Unit>{
        return cle.signinWithSso(authResult)
    }
}