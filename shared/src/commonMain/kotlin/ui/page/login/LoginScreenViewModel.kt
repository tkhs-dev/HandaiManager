package ui.page.login

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import domain.usecase.LoginUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenViewModel(private val loginUseCase: LoginUseCase) {
    data class UiState(
        val userId: String = "",
        val password: String = "",
        val otpCode: String = "",
        val isLoading: Boolean = false,
        val error: String = ""
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var onLoggedIn:() -> Unit = {}
    private var onNeedPassword:() -> Unit = {}
    private var onNeedOtp:() -> Unit = {}

    fun setListeners(onLoggedIn:() -> Unit,onNeedPassword:() -> Unit,onNeedOtp:() -> Unit){
        this.onLoggedIn = onLoggedIn
        this.onNeedPassword = onNeedPassword
        this.onNeedOtp = onNeedOtp
    }

    fun onUserIdChanged(userId: String) {
        _uiState.update { it.copy(userId = userId) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onOtpCodeChanged(otpCode: String) {
        _uiState.update { it.copy(otpCode = otpCode) }
    }

    suspend fun onStartLoginClicked() {
        return coroutineScope {
            _uiState.update { it.copy(isLoading = true) }
            val res = loginUseCase.prepareForLogin()
            _uiState.update { it.copy(isLoading = false) }
            res.onSuccess { status ->
                when(status){
                    LoginUseCase.LoginStatus.NEED_CREDENTIALS -> onNeedPassword()
                    LoginUseCase.LoginStatus.NEED_OTP -> onNeedOtp()
                    LoginUseCase.LoginStatus.SUCCESS -> onLoggedIn()
                }
            }
        }
    }
    suspend fun onAuthPasswordClicked() {
        return coroutineScope {
            _uiState.update { it.copy(isLoading = true) }
            val res = loginUseCase.authPassword(uiState.value.userId,uiState.value.password)
            _uiState.update { it.copy(isLoading = false) }
            res.onSuccess {
                when(it){
                    LoginUseCase.LoginStatus.NEED_CREDENTIALS -> onNeedPassword()
                    LoginUseCase.LoginStatus.NEED_OTP -> onNeedOtp()
                    LoginUseCase.LoginStatus.SUCCESS -> onLoggedIn()
                }
            }.onFailure { _uiState.update { it.copy(error = "Wrong userId or password.Or some error is occurred.") } }
        }
    }
}