package platform

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import java.security.KeyStore
import javax.crypto.KeyGenerator

actual fun getAesKey(): Any {
    val aesSpec = KeyGenParameterSpec.Builder("HandaiManager", KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
        .setKeySize(256)
        .build()

    val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
    keyGenerator.init(aesSpec)
    keyGenerator.generateKey()

    val keyStore = KeyStore.getInstance("AndroidKeyStore")
    keyStore.load(null)

    return keyStore.getKey("HandaiManager", null)
}