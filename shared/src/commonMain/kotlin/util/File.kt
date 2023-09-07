package util

expect object FileUtil{
    fun saveFile(fileName: String, data: ByteArray): Boolean

    /**
     * Saves the file encrypted with AES
     * @param fileName the name of the file to save
     * @param data the data to save
     */
    fun saveFileEncrypted(fileName: String, data: ByteArray): Boolean
    fun loadFile(fileName: String): ByteArray?
    fun loadFileEncrypted(fileName: String): ByteArray?
}
