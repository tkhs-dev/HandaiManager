package data.cache

interface CacheManager {
    fun <T> get(key: String, ignoreExpired:Boolean = false): T?
    fun set(key: String, value: Any, expire: Long = 0)
    fun clear()
}