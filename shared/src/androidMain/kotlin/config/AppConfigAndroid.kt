package config

import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
data class ConfigAndroid(val android1:Boolean = true,val android2:Boolean = true): PlatformConfig

actual fun getDefaultPlatformConfig() = ConfigAndroid() as PlatformConfig
actual val platformSerializerModule = SerializersModule {
    polymorphic(PlatformConfig::class) {
        subclass(ConfigAndroid::class, ConfigAndroid.serializer())
    }
}