package platform

expect fun saveFile(fileName: String, data: ByteArray): Boolean
expect fun saveFileEncrypted(fileName: String, data: ByteArray, key: String): Boolean
