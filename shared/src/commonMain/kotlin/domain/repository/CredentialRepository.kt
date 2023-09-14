package domain.repository

import model.Credential
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import util.FileUtil
import util.Logger

class CredentialRepository {
    companion object{
        const val CREDENTIAL_FILE_NAME = "cred.bin"
    }

    fun loadCredential(): Credential? {
        return FileUtil.loadFileEncrypted(CREDENTIAL_FILE_NAME)
            ?.decodeToString()
            ?.let { Json.decodeFromString<Credential>(it) }
            ?.also {
                if(it != null) {
                    Logger.debug(this::class.simpleName,"Loaded credential for user ${it.userId}")
                }else{
                    Logger.debug(this::class.simpleName,"No credential file found")
                }
            }
    }

    fun saveCredential(credential: Credential) {
        val string = Json.encodeToString(credential)
        val z = FileUtil.saveFileEncrypted(CREDENTIAL_FILE_NAME, string.encodeToByteArray())
        if(z){
            Logger.debug(this::class.simpleName,"Saved credential for user ${credential.userId}")
        }else{
            Logger.debug(this::class.simpleName,"Failed to save credential for user ${credential.userId}")
        }
    }
}