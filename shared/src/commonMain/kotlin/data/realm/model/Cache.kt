package data.realm.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Clock

open class Cache: RealmObject {
    @PrimaryKey
    var key: String = ""
    var value: String = ""
    var expire: Long = 0

    val isExpired: Boolean
        get() = expire > 0 && Clock.System.now().toEpochMilliseconds() > expire
}