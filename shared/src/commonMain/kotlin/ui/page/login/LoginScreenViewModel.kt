package ui.page.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenViewModel() {
    data class UiState(
        val userId: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val error: String = ""
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onUserIdChanged(userId: String) {
        _uiState.update { it.copy(userId = userId) }
        print("changed$userId \n")
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }
    }
}