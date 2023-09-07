package util

expect object TotpUtil {
    fun generateTotpCode(secret: String, timestamp:Long): String
}
