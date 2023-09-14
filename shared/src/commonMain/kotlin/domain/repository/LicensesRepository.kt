package domain.repository

import kotlinx.serialization.json.Json
import model.Licenses
import util.ResourceUtil

class LicensesRepository {
    val format = Json{ignoreUnknownKeys = true}
    suspend fun loadLicenses(): Licenses{
        val json = ResourceUtil.readTextFile("aboutlibraries.json")
        return format.decodeFromString(json) ?: throw Exception("Failed to load licenses")
    }
}