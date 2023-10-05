package data.realm

import data.realm.model.Cache
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmManager {
    private val cache = RealmConfiguration.Builder(setOf(Cache::class))
        .name("cache.realm")
        .schemaVersion(1)
        .build()

    lateinit var cacheRealm: Realm

    fun open(){
        cacheRealm = Realm.open(cache)
    }
}