package platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.github.michaelbull.result.toResultOr
import java.io.File
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Composable
actual fun saveFile(fileName: String, data: ByteArray): Boolean {
    val file = File(LocalContext.current.filesDir, fileName)
    file.writeBytes(data).toResultOr { return false }
    return true;
}

@Composable
actual fun saveFileEncrypted(
    fileName: String,
    data: ByteArray,
    key: Any
): Boolean {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key as Key)
    val encrypted = cipher.doFinal(data)
    return saveFile(fileName, encrypted)
}