package platform

expect fun saveFile(fileName: String, data: ByteArray): Boolean

/**
 * Saves the file encrypted with AES
 * @param fileName the name of the file to save
 * @param data the data to save
 * @param key the AES key to encrypt the data with. The key must be 128/256 bits long.
 */
expect fun saveFileEncrypted(fileName: String, data: ByteArray, key: Any): Boolean
