package config

import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
data class ConfigIos(val ios1:Boolean = true,val ios2:Boolean = true): PlatformConfig

actual fun getDefaultPlatformConfig() = ConfigIos() as PlatformConfig
actual val platformSerializerModule = SerializersModule{
    polymorphic(PlatformConfig::class) {
        subclass(ConfigIos::class, ConfigIos.serializer())
    }
}
