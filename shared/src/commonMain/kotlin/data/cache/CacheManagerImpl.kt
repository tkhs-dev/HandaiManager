package data.cache

import kotlinx.datetime.Clock
import util.Logger

class CacheManagerImpl: CacheManager{
    private val cache = mutableMapOf<String, CachedData>()
    override fun <T> get(key: String, ignoreExpired:Boolean): T? {
        val cachedData = cache[key] ?: return null
        if (cachedData.isExpired && !ignoreExpired) {
            return null
        }
        Logger.debug(this::class.simpleName, "load from cache: $key")
        return cachedData.value as T
    }

    override fun set(key: String, value: Any, expire: Long) {
        cache[key] = CachedData(value, expire)
    }

    override fun clear() {
        cache.clear()
    }
}

private class CachedData(val value: Any, val expire: Long) {
    val isExpired: Boolean
        get() = expire > 0 && Clock.System.now().toEpochMilliseconds() > expire
}