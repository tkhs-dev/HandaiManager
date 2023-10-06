package ui.screen.preference

import domain.usecase.PreferenceUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferenceScreenViewModel(private val preferenceUsecase: PreferenceUsecase) {
    private val _uiState = MutableStateFlow(preferenceUsecase.getConfig())
    val uiState = _uiState.asStateFlow()

    fun clearCache(){
        preferenceUsecase.clearCache()
    }
}