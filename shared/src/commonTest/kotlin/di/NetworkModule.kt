package di

import data.cache.CacheManager
import domain.repository.CleRepository
import domain.repository.IdpRepository
import domain.repository.KoanRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.datetime.Clock
import kotlinx.serialization.KSerializer
import model.User
import network.CleService
import network.IdpService
import network.KoanService
import org.koin.core.module.Module
import org.koin.dsl.module

class NetworkModule {
    companion object{
        fun getCleModule(successGetSamlRequest:Boolean,successAuthSamlSso:Boolean): Module {
            return module{
                single { CleRepository(
                    cacheManager = InMemoryCacheManager(),
                    cleApi = object: CleService {
                    override suspend fun getSamlRequest(): HttpResponse {
                        return HttpClient(MockEngine{ request ->
                            respond(
                                content = ByteReadChannel(""),
                                status = HttpStatusCode.OK,
                                headers = if(successGetSamlRequest) headersOf(HttpHeaders.Location,"https://www.cle.osaka-u.ac.jp/somedir?SAMLRequest=123&SigAlg=123&Signature=123") else headersOf()
                            )
                        }){followRedirects=false}.request()
                    }

                    override suspend fun authSamlSso(
                        samlResponse: String,
                        relayState: String?,
                        button: String
                    ): HttpResponse {
                        return HttpClient(MockEngine{ request ->
                            respond(
                                content = ByteReadChannel(""),
                                status = HttpStatusCode.Found,
                                headers = if(successAuthSamlSso) headersOf(HttpHeaders.Location,"a") else headersOf()
                            )
                        }){followRedirects = false}.request()
                    }

                        override suspend fun getUserInfo(): User {
                            return User(User.Avatar("",""), User.Contact(""),"","", listOf(),
                                User.Job("",""),"", User.Name("","", ""),"",listOf(),"","")
                        }
                    }) }
            }
        }

        fun getKoanModule(successGetSamlRequest:Boolean,successAuthSamlSso:Boolean): Module {
            return module{
                single { KoanRepository(
                    cacheManager = InMemoryCacheManager(),
                    koanApi = object: KoanService {
                    override suspend fun getSamlRequest(): HttpResponse {
                        return HttpClient(MockEngine{ request ->
                            respond(
                                content = ByteReadChannel(""),
                                status = HttpStatusCode.OK,
                                headers = if(successGetSamlRequest) headersOf(HttpHeaders.Location,"https://www.koan.osaka-u.ac.jp/somedir?SAMLRequest=123&SigAlg=123&Signature=123") else headersOf()
                            )
                        }){followRedirects=false}.request()
                    }

                    override suspend fun authSamlSso(
                        samlResponse: String,
                        relayState: String?,
                        button: String
                    ): HttpResponse {
                        return HttpClient(MockEngine{ request ->
                            respond(
                                content = ByteReadChannel(""),
                                status = HttpStatusCode.Found,
                                headers = if(successAuthSamlSso) headersOf(HttpHeaders.Location,"a") else headersOf()
                            )
                        }){followRedirects = false}.request()
                    }

                    override suspend fun getRishuPage(): HttpResponse {
                        TODO("Not yet implemented")
                    }

                    override suspend fun getTimeTable(): HttpResponse {
                        TODO("Not yet implemented")
                    }

                    override suspend fun getTimeTableByTerm(
                        flowExecutionKey: String,
                        gakkiKbnCode: Int
                    ): HttpResponse {
                        TODO("Not yet implemented")
                    }
                }) }
            }
        }

        fun getIdpModule(successConnectSsoSite:Boolean,needAuthPassword:Boolean,needAuthMfa:Boolean,successAuthPassword:Boolean,successAuthMfa:Boolean,successRoleSelect:Boolean): Module {
            return module{
                single { IdpRepository(
                    cacheManager = InMemoryCacheManager(),
                    idpApi = object: IdpService {
                    override suspend fun connectSsoSite(
                        samlRequest: String,
                        relayState: String?,
                        sigAlg: String,
                        signature: String
                    ): HttpResponse {
                        return HttpClient(MockEngine{request ->
                            respond(
                                content = ByteReadChannel(if(needAuthPassword) "" else "利用者選択"),
                                status = if(successConnectSsoSite) HttpStatusCode.OK else HttpStatusCode.BadRequest
                            )
                        }).request()
                    }

                    override suspend fun refreshAuthPage() {

                    }

                    override suspend fun authPassword(
                        userid: String,
                        password: String,
                        checkbox: String,
                        submit: String
                    ): String {
                        return if(!successAuthPassword) "認証エラー" else if(needAuthMfa) "MFA" else "利用者選択"
                    }

                    override suspend fun authMfa(otp: String, submit: String): String {
                        return if(successAuthMfa)"利用者選択" else "認証エラー"
                    }

                    override suspend fun roleSelect(): String {
                        return if(successRoleSelect) "name=\"SAMLResponse\" value=\"123\">\n name=\"RelayState\" value=\"null\">\n" else ""
                    }

                }) }
            }
        }
    }
}

class InMemoryCacheManager:CacheManager{
    private val cache = mutableMapOf<String, CachedData>()
    override fun <T> get(key: String, serializer: KSerializer<T>, ignoreExpired: Boolean): T? {
        val cachedData = cache[key] ?: return null
        if (cachedData.isExpired && !ignoreExpired) {
            return null
        }
        return cachedData.value as T
    }

    override fun <T> set(key: String, serializer: KSerializer<T>, value: T, expire: Long) {
        cache[key] = CachedData(value, expire)
    }

    override fun clear() {
        throw UnsupportedOperationException()
    }

    private class CachedData(val value: Any?, val expire: Long) {
        val isExpired: Boolean
            get() = expire > 0 && Clock.System.now().toEpochMilliseconds() > expire
    }
}
