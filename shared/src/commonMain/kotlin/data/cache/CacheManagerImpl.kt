package data.cache

import data.realm.RealmManager
import data.realm.model.Cache
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.query
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import util.Logger

class CacheManagerImpl(private val realmManager: RealmManager): CacheManager{
    private val format = Json{ignoreUnknownKeys = true}
    override fun <T> get(key: String, serializer: KSerializer<T>, ignoreExpired:Boolean): T? {
        val cachedData = realmManager.cacheRealm.query<Cache>("key == $0",key).first()
            .find()
            ?.copyFromRealm() ?: return null
        if (cachedData.isExpired && !ignoreExpired) {
            return null
        }
        Logger.debug(this::class.simpleName, "load from cache: $key")
        return format.decodeFromString(serializer, cachedData.value)
    }

    override fun <T> set(key: String, serializer: KSerializer<T>, value: T, expire: Long) {
        realmManager.cacheRealm.writeBlocking {
            this.copyToRealm(Cache().apply {
                this.key = key
                this.value = format.encodeToString(serializer, value)
                this.expire = expire
            }, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override fun clear() {
        realmManager.cacheRealm.writeBlocking {
            delete(query<Cache>().find())
        }
    }
}