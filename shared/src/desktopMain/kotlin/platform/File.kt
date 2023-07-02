package platform

import java.io.File
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

actual fun saveFile(fileName: String, data: ByteArray): Boolean {
    val file = File(fileName)
    file.writeBytes(data)
    return true
}

actual fun saveFileEncrypted(
    fileName: String,
    data: ByteArray,
    key: String
): Boolean {
    val keyData = MessageDigest.getInstance("SHA-256").digest(key.encodeToByteArray())
    val skeySpec = SecretKeySpec(keyData, "AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
    val encrypted = cipher.doFinal(data)
    return saveFile(fileName, encrypted)
}