package util

import platform.getAesKey
import java.io.File
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

actual object FileUtil{
    lateinit var filesDir:File

    actual fun saveFile(fileName: String, data: ByteArray): Boolean {
        val file = File(filesDir, fileName)
        return kotlin.runCatching {
            file.writeBytes(data)
        }.isSuccess
    }

    actual fun saveFileEncrypted(
        fileName: String,
        data: ByteArray
    ): Boolean {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val key = getAesKey() as Key
        cipher.init(Cipher.ENCRYPT_MODE, key)
        saveFile("${fileName}_iv.bin",cipher.iv)
        val encrypted = cipher.doFinal(data)
        return saveFile(fileName, encrypted)
    }

    actual fun loadFile(fileName: String): ByteArray? {
        val file = File(filesDir, fileName)
        return runCatching { file.readBytes() }
            .getOrNull()
    }

    actual fun loadFileEncrypted(fileName: String): ByteArray? {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val key = getAesKey() as Key
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(loadFile("${fileName}_iv.bin") ?: ByteArray(16)))
        return loadFile(fileName)?.let { cipher.doFinal(it) }
    }
}