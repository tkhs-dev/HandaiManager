package util

expect object ResourceUtil {
    suspend fun readTextFile(fileName: String): String
}