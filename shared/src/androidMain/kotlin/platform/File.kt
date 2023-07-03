package platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.github.michaelbull.result.get
import com.github.michaelbull.result.toResultOr
import java.io.File
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

@Composable
actual fun saveFile(fileName: String, data: ByteArray): Boolean {
    val file = File(LocalContext.current.filesDir, fileName)
    file.writeBytes(data).toResultOr { return false }
    return true
}

@Composable
actual fun saveFileEncrypted(
    fileName: String,
    data: ByteArray
): Boolean {
    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
    val key = getAesKey() as Key
    cipher.init(Cipher.ENCRYPT_MODE, key)
    saveFile("iv.bin",cipher.iv)
    val encrypted = cipher.doFinal(data)
    return saveFile(fileName, encrypted)
}

@Composable
actual fun loadFile(fileName: String): ByteArray? {
    val file = File(LocalContext.current.filesDir, fileName)
    return file.readBytes().toResultOr { return null }.get()
}

@Composable
actual fun loadFileEncrypted(fileName: String): ByteArray? {
    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
    val key = getAesKey() as Key
    cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(loadFile("iv.bin") ?: ByteArray(16)))
    val file = File(LocalContext.current.filesDir, fileName)
    return cipher.doFinal(file.readBytes()).toResultOr { return null }.get()
}