import com.github.michaelbull.result.get
import di.NetworkModule
import kotlinx.coroutines.runBlocking
import network.Idp
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.*

class IdpTest: KoinTest{
    @BeforeTest
    fun beforeTest(){
        startKoin {
            modules(
                NetworkModule.getIdpModule(true,true,true,true,true,true)
            )
        }
    }
    @AfterTest
    fun afterTest(){
        stopKoin()
    }

    @Test
    fun testAuthenticate(){
        val idp = get<Idp>()
        val result = runBlocking {
            idp.authenticate(Idp.AuthRequestData("123","123","123","123"),"123","123","123")
        }
            .get()
        assertNotNull(result)

    }
}