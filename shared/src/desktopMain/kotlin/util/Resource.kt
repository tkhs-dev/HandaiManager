package util

actual object ResourceUtil {
    actual suspend fun readTextFile(fileName: String): String {
        this::class.java.getResourceAsStream("/$fileName").use {
            return it.bufferedReader().readText()
        }
    }
}