package ui.screen.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ui.screen.login.LoginScreenViewModel

class HomeScreenViewModel {
    data class UiState(
        val selectedPage:HomeScreenPage = HomeScreenPage.Dashboard
    )

    private val _uiState = MutableStateFlow(HomeScreenViewModel.UiState())
    val uiState = _uiState.asStateFlow()

    fun onNavigationDrawerClicked(selected:HomeScreenPage){
        _uiState.update { it.copy(selectedPage = selected) }
    }
}