package network

import com.github.michaelbull.result.get
import di.NetworkModule
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

class CleTest:KoinTest {
    @BeforeTest
    fun beforeTest(){
        startKoin {
            modules(
                NetworkModule.getCleModule(true,true),
            )
        }
    }

    @AfterTest
    fun afterTest(){
        stopKoin()
    }
    @Test
    fun testGetAuthRequestData() {
        val cle = get<Cle>()
        val res = runBlocking {
            cle.getAuthRequestData()
        }
        assertTrue(res.get()?.samlRequest.equals("123")&&res.get()?.relayState == null&&res.get()?.sigAlg.equals("123")&&res.get()?.signature.equals("123"))
    }
    @Test
    fun testLogin(){
        val cle = get<Cle>()
        val res = runBlocking {
            cle.signinWithSso(Idp.AuthResult("123","123"))
        }
        assertNull(res.component2())
    }
}