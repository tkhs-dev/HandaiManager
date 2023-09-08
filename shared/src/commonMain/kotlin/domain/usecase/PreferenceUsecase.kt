package domain.usecase

import config.AppConfig
import config.ConfigShared
import config.getDefaultPlatformConfig
import domain.repository.ConfigRepository

class PreferenceUsecase(private val configRepository: ConfigRepository) {
    fun getConfig(): AppConfig{
        return configRepository.loadConfig() ?: AppConfig(shared = ConfigShared(), platform = getDefaultPlatformConfig()).also { configRepository.saveConfig(it) }
    }
}