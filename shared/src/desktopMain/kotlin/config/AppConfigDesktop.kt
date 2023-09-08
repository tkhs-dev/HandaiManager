package config

import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
data class ConfigDesktop(
    val desktop1:Boolean = true,
    val desktop2:Boolean = true,
    val desktop3:String = "aaa",
): PlatformConfig

actual fun getDefaultPlatformConfig() = ConfigDesktop() as PlatformConfig
actual val platformSerializerModule = SerializersModule {
    polymorphic(PlatformConfig::class) {
        subclass(ConfigDesktop::class,ConfigDesktop.serializer())
    }
}