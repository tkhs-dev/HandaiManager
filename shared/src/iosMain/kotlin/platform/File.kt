package platform

actual fun saveFile(fileName: String, data: ByteArray): Boolean {
    TODO()
}

actual fun saveFileEncrypted(
    fileName: String,
    data: ByteArray
): Boolean {
    TODO()
}

actual fun loadFile(fileName: String): ByteArray? {
    TODO()
}

actual fun loadFileEncrypted(fileName: String): ByteArray? {
    TODO()
}