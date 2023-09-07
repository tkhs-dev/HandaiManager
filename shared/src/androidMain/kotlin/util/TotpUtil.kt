package util

import com.google.common.io.BaseEncoding
import java.nio.ByteBuffer
import java.security.Key
import java.util.Locale
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

actual object TotpUtil {
    actual fun generateTotpCode(secret: String,timestamp: Long): String {
        val mac = Mac.getInstance("HmacSHA1")
        val buffer = ByteBuffer.allocate(mac.macLength)

        buffer.putLong(0, timestamp/30)
        val array = buffer.array()
        mac.init(generateKeyFromSecret(secret))
        mac.update(array, 0, 8)
        mac.doFinal(array, 0)

        val offset:Int = buffer.get(buffer.capacity() -1).and(0x0f).toInt()
        return buffer.getInt(offset).and(0x7fffffff).mod(1_000_000)
            .let { String.format(Locale.getDefault(),"%06d",it) }
    }

    private fun generateKeyFromSecret(secret:String): Key {
        return SecretKeySpec(BaseEncoding.base32().decode(secret), "HmacSHA1")
    }
}