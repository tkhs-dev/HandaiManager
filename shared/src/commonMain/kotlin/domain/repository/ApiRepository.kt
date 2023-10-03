package domain.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.runCatching
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import data.network.ApiError
import util.Logger

abstract class ApiRepository {
    protected suspend inline fun tryCallApi(
        ignoreAuthError:Boolean = false,
        onCatchException: (Throwable) -> Unit = {},
        onResponseFailure: (HttpResponse) -> Unit = {},
        block: () -> HttpResponse
    ): Result<HttpResponse, ApiError> {
        return runCatching {
            block()
        }.onFailure {
            Logger.error(this::class.simpleName, it)
            onCatchException(it)
        }.mapError {
            ApiError.InternalException(it)
        }.flatMap {
            if (it.status.isSuccess() || it.status== HttpStatusCode.Found) {
                if(it.headers["Location"]?.contains("sso_redirect") == true && !ignoreAuthError ){
                    Err(ApiError.AuthError)
                }else{
                    Ok(it)
                }
            } else {
                onResponseFailure(it)
                if(it.status == HttpStatusCode.Unauthorized){
                    Err(ApiError.AuthError)
                } else{
                    Err(ApiError.InvalidResponse(it.status.value, it.bodyAsText()))
                }
            }
        }
    }
}