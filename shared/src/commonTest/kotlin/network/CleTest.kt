package network

import com.github.michaelbull.result.flatMap
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertNull

class CleTest {
    @Test
    fun testGetAuthRequestData() {
        val res = runBlocking {
            Cle.getAuthRequestData()
                .flatMap {
                    Idp.authenticate(it,"","","")
                }
                .flatMap {
                    Cle.signinWithSso(it)
                }
        }
        assertNull(res.component2())
    }
}