package util

import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals

class TotpUtilTest {
    @Test
    fun testGenerateTotpCode(){
        assertEquals(TotpUtil.generateTotpCode("JBSWY3DPEHPK3PXP",1693983700), "365525")
    }
}