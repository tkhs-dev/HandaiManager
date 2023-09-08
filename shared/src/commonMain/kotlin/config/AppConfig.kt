package config

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule

interface PlatformConfig

@Serializable
data class ConfigShared(
    val shared1: Boolean = true,
    val shared2: Boolean = true
)

@Serializable
data class AppConfig(val shared: ConfigShared, @Polymorphic val platform: PlatformConfig)

expect fun getDefaultPlatformConfig(): PlatformConfig

expect val platformSerializerModule:SerializersModule