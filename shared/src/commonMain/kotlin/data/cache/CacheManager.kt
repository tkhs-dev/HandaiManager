package data.cache

import kotlinx.serialization.KSerializer

interface CacheManager {
    fun <T> get(key: String,serializer: KSerializer<T>, ignoreExpired:Boolean = false): T?
    fun <T> set(key: String, serializer: KSerializer<T>, value: T, expire: Long = 0)
    fun getAllKeys(): List<String>
    fun clear()
}