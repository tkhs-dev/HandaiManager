package ui.screen.license

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.repository.LicensesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Licenses

class LicenseScreenModel(private val licensesRepository: LicensesRepository):ScreenModel {
    var licenses = MutableStateFlow<Licenses?>(null)
    suspend fun onLaunched() {
        coroutineScope.launch{
            licenses.update { licensesRepository.loadLicenses() }
        }
    }
}