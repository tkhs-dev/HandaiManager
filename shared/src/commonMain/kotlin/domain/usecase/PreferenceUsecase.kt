package domain.usecase

import config.AppConfig
import config.ConfigShared
import config.getDefaultPlatformConfig
import data.cache.CacheManager
import domain.repository.ConfigRepository

class PreferenceUsecase(private val configRepository: ConfigRepository, private val cacheManager: CacheManager) {
    fun getConfig(): AppConfig{
        return configRepository.loadConfig() ?: AppConfig(shared = ConfigShared(), platform = getDefaultPlatformConfig()).also { configRepository.saveConfig(it) }
    }

    fun clearCache(){
        cacheManager.clear()
    }
}