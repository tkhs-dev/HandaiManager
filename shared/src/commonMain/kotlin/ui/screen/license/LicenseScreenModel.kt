package ui.screen.license

import cafe.adriel.voyager.core.model.ScreenModel
import domain.repository.LicensesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.Licenses

class LicenseScreenModel(private val licensesRepository: LicensesRepository):ScreenModel {
    var licenses = MutableStateFlow<Licenses?>(null)
    suspend fun onLaunched() {
        licenses.update { licensesRepository.loadLicenses() }
    }
}