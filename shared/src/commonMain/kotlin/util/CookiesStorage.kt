package util

import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.toResultOr
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.CookieEncoding
import io.ktor.http.Url
import io.ktor.http.hostIsIp
import io.ktor.http.isSecure
import io.ktor.util.date.GMTDate
import io.ktor.util.date.getTimeMillis
import io.ktor.util.toLowerCasePreservingASCIIRules
import kotlinx.atomicfu.AtomicLong
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.math.min

class FileCookiesStorage: CookiesStorage {
    private var container: MutableList<Cookie> = mutableListOf()
    private val oldestCookie: AtomicLong = atomic(0L)
    private val mutex = Mutex()

    @Serializable
    data class CookieStorageSerializable(val oldestCookie:Long, val container:List<CookieSerializable>)

    suspend fun loadCookies(){
        withContext(Dispatchers.IO) {
            FileUtil.loadFileEncrypted("cookies.bin")
        }.toResultOr {
            Logger.debug(this::class.simpleName, "Failed to load cookies from file: file not found")
        }.onSuccess { it ->
            Json.decodeFromString<CookieStorageSerializable>(it.decodeToString()).let {
                oldestCookie.value = it.oldestCookie
                container = it.container.map { cookie ->
                    cookie.toCookie()
                }.toMutableList()
            }
            Logger.debug(this::class.simpleName, "loadCookies: $container")
        }
    }

    override suspend fun get(requestUrl: Url): List<Cookie> = mutex.withLock {
        val now = getTimeMillis()
        if (now >= oldestCookie.value) cleanup(now)

        return@withLock container.filter { it.matches(requestUrl) }.map { it.copy(encoding = CookieEncoding.DQUOTES) }
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie): Unit = mutex.withLock {
        with(cookie) {
            if (name.isBlank()) return@withLock
        }

        container.removeAll { it.name == cookie.name && it.matches(requestUrl) }
        container.add(cookie.fillDefaults(requestUrl))
        cookie.expires?.timestamp?.let { expires ->
            if (oldestCookie.value > expires) {
                oldestCookie.value = expires
            }
        }

        CookieStorageSerializable(oldestCookie.value, container.map { it.toSerializable() })
            .let {
                Json.encodeToString(it)
            }.encodeToByteArray()
            .let {
                withContext(Dispatchers.IO){
                    FileUtil.saveFileEncrypted("cookies.bin", it)
                }
            }
    }

    override fun close() {
    }

    private fun cleanup(timestamp: Long) {
        container.removeAll { cookie ->
            val expires = cookie.expires?.timestamp ?: return@removeAll false
            expires < timestamp
        }

        val newOldest = container.fold(Long.MAX_VALUE) { acc, cookie ->
            cookie.expires?.timestamp?.let { min(acc, it) } ?: acc
        }

        oldestCookie.value = newOldest
    }
}

@Serializable
data class CookieSerializable(val name:String, val value:String, val encoding: CookieEncoding, val maxAge:Int, val expires:Instant?, val domain:String?, val path:String?, val secure:Boolean, val httpOnly:Boolean, val extensions:Map<String,String?>){
    companion object{
        fun fromCookie(cookie: Cookie):CookieSerializable{
            return CookieSerializable(cookie.name,cookie.value,CookieEncoding.DQUOTES,cookie.maxAge,
                cookie.expires?.let { Instant.fromEpochMilliseconds(it.timestamp) },cookie.domain,cookie.path,cookie.secure,cookie.httpOnly,cookie.extensions)
        }
    }

    fun toCookie(): Cookie{
        return Cookie(name,value,CookieEncoding.DQUOTES,maxAge, expires?.epochSeconds?.let{GMTDate(it)},domain,path,secure,httpOnly,extensions)
    }
}

fun Cookie.toSerializable():CookieSerializable{
    return CookieSerializable.fromCookie(this)
}

fun Cookie.matches(requestUrl: Url): Boolean {
    val domain = domain?.toLowerCasePreservingASCIIRules()?.trimStart('.')
        ?: error("Domain field should have the default value")

    val path = with(path) {
        val current = path ?: error("Path field should have the default value")
        if (current.endsWith('/')) current else "$path/"
    }

    val host = requestUrl.host.toLowerCasePreservingASCIIRules()
    val requestPath = let {
        val pathInRequest = requestUrl.encodedPath
        if (pathInRequest.endsWith('/')) pathInRequest else "$pathInRequest/"
    }

    if (host != domain && (hostIsIp(host) || !host.endsWith(".$domain"))) {
        return false
    }

    if (path != "/" &&
        requestPath != path &&
        !requestPath.startsWith(path)
    ) return false

    return !(secure && !requestUrl.protocol.isSecure())
}

fun Cookie.fillDefaults(requestUrl: Url): Cookie {
    var result = this

    if (result.path?.startsWith("/") != true) {
        result = result.copy(path = requestUrl.encodedPath)
    }

    if (result.domain.isNullOrBlank()) {
        result = result.copy(domain = requestUrl.host)
    }

    return result
}