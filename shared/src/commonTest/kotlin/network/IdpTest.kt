import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.get
import kotlinx.coroutines.runBlocking
import network.Cle
import network.Idp
import kotlin.test.*
class IdpTest{
    @Test
    fun testAutheenticate(){
        val result = runBlocking {
            Cle.getAuthRequestData()
                .flatMap {
                    Idp.authenticate(it,"","","")
                }
        }
            .get()
        assertNotNull(result)

    }
}