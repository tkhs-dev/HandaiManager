package ui.screen.license

import domain.repository.LicensesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.Licenses

class LicenseScreenViewModel(private val licensesRepository: LicensesRepository) {
    var licenses = MutableStateFlow<Licenses?>(null)
    suspend fun onLaunched() {
        licenses.update { licensesRepository.loadLicenses() }
    }
}