package network

import com.github.michaelbull.result.get
import network.AuthResponseData
import di.NetworkModule
import domain.repository.KoanRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

class KoanTest: KoinTest {
    @BeforeTest
    fun beforeTest(){
        startKoin {
            modules(
                NetworkModule.getKoanModule(true,true),
            )
        }
    }

    @AfterTest
    fun afterTest(){
        stopKoin()
    }
    @Test
    fun testGetAuthRequestData() {
        val cle = get<KoanRepository>()
        val res = runBlocking {
            cle.getAuthRequest()
        }
        assertTrue(res.get()?.samlRequest.equals("123")&&res.get()?.relayState == null&&res.get()?.sigAlg.equals("123")&&res.get()?.signature.equals("123"))
    }
    @Test
    fun testLogin(){
        val cle = get<KoanRepository>()
        val res = runBlocking {
            cle.signinWithSso(AuthResponseData("123","123"))
        }
        assertNull(res.component2())
    }
}