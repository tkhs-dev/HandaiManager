import com.github.michaelbull.result.get
import di.NetworkModule
import domain.repository.IdpRepository
import kotlinx.coroutines.runBlocking
import network.AuthRequestData
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

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
        val idp = get<IdpRepository>()
        val result = runBlocking {
            idp.authenticate(AuthRequestData("123","123","123","123"),"123","123","123")
        }
            .get()
        assertNotNull(result)

    }
}