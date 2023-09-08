package domain.repository

import config.AppConfig
import config.platformSerializerModule
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import util.FileUtil
import util.Logger

class ConfigRepository {
    companion object{
        const val CONFIG_FILE_NAME = "config.json"
    }

    private val format = Json{serializersModule = platformSerializerModule}

    fun loadConfig(): AppConfig? {
        return kotlin.runCatching {
            FileUtil.loadFile(CONFIG_FILE_NAME)
                ?.decodeToString()
                ?.let { format.decodeFromString<AppConfig>(it) }
                .also {
                    if(it != null) {
                        Logger.debug(this::class.simpleName,"Config loaded")
                    }else{
                        Logger.debug(this::class.simpleName,"No config file found")
                    }
                }
        }.getOrNull()
    }

    fun saveConfig(config: AppConfig) {
        val string = format.encodeToString(config)
        val z = FileUtil.saveFile(CONFIG_FILE_NAME, string.encodeToByteArray())
        if(z){
            Logger.debug(this::class.simpleName,"Saved config")
        }else{
            Logger.debug(this::class.simpleName,"Failed to save config")
        }
    }
}