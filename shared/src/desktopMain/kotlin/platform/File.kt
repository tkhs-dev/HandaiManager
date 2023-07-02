package platform

import com.github.michaelbull.result.toResultOr
import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

actual fun saveFile(fileName: String, data: ByteArray): Boolean {
    val file = File(fileName)
    file.writeBytes(data).toResultOr { return false }
    return true
}

actual fun saveFileEncrypted(
    fileName: String,
    data: ByteArray,
    key: Any
): Boolean {
    val skeySpec = getAesKey() as SecretKeySpec
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
    val encrypted = cipher.doFinal(data)
    return saveFile(fileName, encrypted)
}