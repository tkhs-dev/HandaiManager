import network.Idp
import kotlin.test.*
class IdpTest{
    @Test
    fun testAutheenticate(){
        val result = Idp.authenticate("test", "test","test","test")
        print(result.status.name)
        print("\n")
        assertEquals(result.status,Idp.AuthStatus.SUCCESS)
    }
}