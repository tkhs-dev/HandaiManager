package domain.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.toErrorIfNull
import com.github.michaelbull.result.toResultOr
import data.cache.CacheManager
import network.ApiError
import network.AuthRequestData
import network.AuthResponseData
import network.KoanService
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import model.TimeTable
import util.FileCookiesStorage
import util.HtmlUtil

class KoanRepository(
    private val cacheManager: CacheManager,
    private val fileCookiesStorage: FileCookiesStorage? = null,
    private val koanApi: KoanService =
        Ktorfit.Builder().httpClient(HttpClient {
            followRedirects = false
            install(HttpCookies){
                storage = fileCookiesStorage ?: AcceptAllCookiesStorage()
            }
        }).baseUrl(KoanService.BASE_URL)
            .build()
            .create(),
    private val koanApiRedirectable: KoanService =
        Ktorfit.Builder().httpClient(HttpClient {
            followRedirects = true
            install(HttpCookies){
                storage = fileCookiesStorage ?: AcceptAllCookiesStorage()
            }
        }).baseUrl(KoanService.BASE_URL)
            .build()
            .create()
): ApiRepository(cacheManager), SSOApiRepository {
    override suspend fun getAuthRequest(): Result<AuthRequestData, ApiError> {
        return withContext(Dispatchers.IO) {
            validateHttpResponse(ignoreAuthError = true) {
                koanApi.getSamlRequest()
            }.flatMap {
                it.headers[HttpHeaders.Location]?.let { Url(it).parameters }
                    .toResultOr { ApiError.InvalidResponse(it.status.value, it.bodyAsText()) }
            }.flatMap {
                if (it["SAMLRequest"] == null || it["SigAlg"] == null || it["Signature"] == null) {
                    Err(ApiError.Unknown)
                } else {
                    Ok(
                        AuthRequestData(
                            it["SAMLRequest"]!!,
                            it["RelayState"],
                            it["SigAlg"]!!,
                            it["Signature"]!!
                        )
                    )
                }
            }
        }
    }

    override suspend fun signinWithSso(authResponseData: AuthResponseData): Result<Unit, ApiError> {
        return withContext(Dispatchers.IO) {
            validateHttpResponse {
                koanApi.authSamlSso(
                    authResponseData.samlResponse,
                    authResponseData.relayState ?: "null"
                )
            }.flatMap {
                if (it.status == HttpStatusCode.Found) {
                    Ok(Unit)
                } else {
                    Err(ApiError.InvalidResponse(it.status.value, it.bodyAsText()))
                }
            }
        }
    }

    suspend fun getTimeTable(term:TimeTable.Term, year:Int):Result<TimeTable, ApiError>{
        return useCache("timetable_${term.name}_$year") {
            setExpire(Clock.System.now().toEpochMilliseconds()+1000*60)
            koanApiRedirectable.getRishuPage()
            withContext(Dispatchers.IO) {
                validateHttpResponse {
                    koanApiRedirectable.getTimeTable()
                }.flatMap {
                    val body = it.bodyAsText()
                    val nterm = Regex("""(\d{4})年度　(春学期|夏学期|秋学期|冬学期)""").find(body)
                        ?.groupValues?.get(2)?.let { TimeTable.Term.fromJpText(it) }
                        ?: return@flatMap Err(
                            ApiError.InvalidResponse(
                                it.status.value,
                                it.bodyAsText()
                            )
                        )
                    if (nterm == term) {
                        Ok(it)
                    } else {
                        val fekey =
                            it.request.url.parameters["_flowExecutionKey"] ?: return@flatMap Err(
                                ApiError.InvalidResponse(it.status.value, it.bodyAsText())
                            )
                        val gakkiKbnCode = when (term) {
                            TimeTable.Term.SPRING -> 3
                            TimeTable.Term.SUMMER -> 4
                            TimeTable.Term.AUTUMN -> 5
                            TimeTable.Term.WINTER -> 6
                        }
                        validateHttpResponse {
                            koanApiRedirectable.getTimeTableByTerm(fekey, gakkiKbnCode)
                        }
                    }
                }.flatMap {
                    com.github.michaelbull.result.runCatching {
                        HtmlUtil.parseTimeTable(it.bodyAsText())
                    }.mapError {
                        ApiError.ParseError(it.message)
                    }.toErrorIfNull {
                        ApiError.ParseError(null)
                    }
                }
            }
        }
    }
}