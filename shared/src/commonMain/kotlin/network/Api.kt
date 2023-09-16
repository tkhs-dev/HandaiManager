package network

sealed class ApiError {
    data class InvalidResponse(val responseCode: Int, val message: String?) : ApiError()
    data class InternalException(val throwable: Throwable) : ApiError()
    data class ParseError(val message: String?) : ApiError()
    object AuthError : ApiError()
    object WrongCredentials : ApiError()
    object Unknown : ApiError()
}
