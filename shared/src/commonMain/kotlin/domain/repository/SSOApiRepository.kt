package domain.repository

import com.github.michaelbull.result.Result
import data.network.ApiError
import data.network.AuthRequestData
import data.network.AuthResponseData

/**
 * 大阪大学の認証サービスを利用するAPIを扱うリポジトリ
 */
interface SSOApiRepository{
    suspend fun getAuthRequest(): Result<AuthRequestData, ApiError>
    suspend fun signinWithSso(authResponseData: AuthResponseData): Result<Unit, ApiError>
}