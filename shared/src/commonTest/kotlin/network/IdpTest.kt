import com.github.michaelbull.result.get
import kotlinx.coroutines.runBlocking
import network.Cle
import network.Idp
import kotlin.test.*
class IdpTest{
    @Test
    fun testAutheenticate(){
        val authReqData = runBlocking {
            Cle.getAuthRequestData()
        } ?: return
        val result = runBlocking {
            Idp.authenticate(authReqData,"","","")
        }
        assertEquals(result.get() == null, true)
    }
}