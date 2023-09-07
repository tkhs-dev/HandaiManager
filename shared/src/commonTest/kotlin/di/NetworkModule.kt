package di

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import network.Cle
import network.CleService
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

class NetworkModule {
    companion object{
        fun getCleModule(successGetSamlRequest:Boolean,successAuthSamlSso:Boolean): Module {
            return module{
                single { Cle(object: CleService {
                    override suspend fun getSamlRequest(): HttpResponse {
                        return HttpClient(MockEngine{ request ->
                            respond(
                                content = ByteReadChannel(""),
                                status = HttpStatusCode.OK,
                                headers = if(successGetSamlRequest) headersOf(HttpHeaders.Location,"https://www.cle.osaka-u.ac.jp/somedir?SAMLRequest=123&SigAlg=123&Signature=123") else headersOf()
                            )
                        }).request()
                    }

                    override suspend fun authSamlSso(
                        samlResponse: String,
                        relayState: String?,
                        button: String
                    ): HttpResponse {
                        return HttpClient(MockEngine{ request ->
                            respond(
                                content = ByteReadChannel(""),
                                status = HttpStatusCode.OK,
                                headers = if(successAuthSamlSso) headersOf(HttpHeaders.Location,"") else headersOf()
                            )
                        }).request()
                    }
                }) }
            }
        }
        fun getIdpModule(successConnectSsoSite:Boolean,needAuthPassword:Boolean,needAuthMfa:Boolean,successAuthPassword:Boolean,successAuthMfa:Boolean,successRoleSelect:Boolean): Module {
            return module{
                single { network.Idp(object: network.IdpService {
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