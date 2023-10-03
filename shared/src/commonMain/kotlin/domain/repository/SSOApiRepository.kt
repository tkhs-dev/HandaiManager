package domain.repository

import com.github.michaelbull.result.Result
import network.ApiError
import network.AuthRequestData
import network.AuthResponseData

/**
 * 大阪大学の認証サービスを利用するAPIを扱うリポジトリ
 */
interface SSOApiRepository{
    suspend fun getAuthRequest(): Result<AuthRequestData, ApiError>
    suspend fun signinWithSso(authResponseData: AuthResponseData): Result<Unit, ApiError>
}