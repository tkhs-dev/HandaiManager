package network

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class CleTest {
    @Test
    fun testGetAuthRequestData() {
        val result = runBlocking {
            Cle.getAuthRequestData()
        }
        print(result)
        print("\n")
        assertEquals(result, null)
    }
}