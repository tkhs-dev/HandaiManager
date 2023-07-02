package platform

import java.net.InetAddress
import java.security.MessageDigest
import javax.crypto.spec.SecretKeySpec

actual fun getAesKey(): Any {
    val str = InetAddress.getLocalHost().hostName
    val key = MessageDigest.getInstance("SHA-256").digest(str.encodeToByteArray())
    return SecretKeySpec(key, "AES")
}