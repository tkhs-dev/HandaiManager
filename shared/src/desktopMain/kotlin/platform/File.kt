package platform

import com.github.michaelbull.result.get
import com.github.michaelbull.result.toResultOr
import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

actual fun saveFile(fileName: String, data: ByteArray): Boolean {
    val file = File(fileName)
    file.writeBytes(data).toResultOr { return false }
    return true
}

actual fun saveFileEncrypted(
    fileName: String,
    data: ByteArray
): Boolean {
    val key = getAesKey() as SecretKeySpec
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(ByteArray(16)))
    val encrypted = cipher.doFinal(data)
    return saveFile(fileName, encrypted)
}

actual fun loadFile(fileName: String): ByteArray? {
    val file = File(fileName)
    return file.readBytes().toResultOr { return null }.get()
}

actual fun loadFileEncrypted(fileName: String): ByteArray? {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val key = getAesKey() as SecretKeySpec
    cipher.init(Cipher.DECRYPT_MODE, key,IvParameterSpec(ByteArray(16)))
    val file = File(fileName)
    return cipher.doFinal(file.readBytes()).toResultOr { return null }.get()
}