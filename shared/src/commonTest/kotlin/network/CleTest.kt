package network

import com.github.michaelbull.result.get
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class CleTest {
    @Test
    fun testGetAuthRequestData() {
        val authReqData = runBlocking {
            Cle.getAuthRequestData()
        } ?: return
        val result = runBlocking {
            Idp.authenticate(authReqData,"","","")
        }
        val result2 = runBlocking {
            Cle.signinWithSso(result.get()!!)
        }
        assertEquals(result2.component1(), Unit)
    }
}