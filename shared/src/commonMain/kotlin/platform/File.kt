package platform

expect fun saveFile(fileName: String, data: ByteArray): Boolean

/**
 * Saves the file encrypted with AES
 * @param fileName the name of the file to save
 * @param data the data to save
 */
expect fun saveFileEncrypted(fileName: String, data: ByteArray): Boolean

expect fun loadFile(fileName: String): ByteArray?
expect fun loadFileEncrypted(fileName: String): ByteArray?
