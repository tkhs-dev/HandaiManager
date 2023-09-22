package ui.screen.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.toResultOr
import dev.icerock.moko.resources.StringResource
import dev.tkhs.handaimanager.MR
import domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Credential

class LoginScreenModel(private val loginUseCase: LoginUseCase): ScreenModel {
    data class UiState(
        val userId: String = "",
        val password: String = "",
        val otpCode: String = "",
        val isLoading: Boolean = false,
        val error: StringResource? = null
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
        coroutineScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            loginUseCase.prepareForLoginCle()
                .also {
                    _uiState.update { it.copy(isLoading = false) }
                }
                .onSuccess { status ->
                    _uiState.update { it.copy(error = null) }
                    when(status){
                        LoginUseCase.LoginStatus.NEED_CREDENTIALS -> onNeedPassword()
                        LoginUseCase.LoginStatus.NEED_MFA -> onNeedOtp()
                        LoginUseCase.LoginStatus.SUCCESS -> onLoggedIn()
                    }
                }
        }
    }

    suspend fun onAuthPasswordClicked() {
        coroutineScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            loginUseCase.authPassword(uiState.value.userId,uiState.value.password)
                .also {
                    _uiState.update { it.copy(isLoading = false) }
                }
                .onSuccess{
                    _uiState.update { it.copy(error = null) }
                    when(it){
                        LoginUseCase.LoginStatus.NEED_CREDENTIALS -> onNeedPassword()
                        LoginUseCase.LoginStatus.NEED_MFA -> onNeedOtp()
                        LoginUseCase.LoginStatus.SUCCESS -> onLoggedIn()
                    }
                }.onFailure { _uiState.update { it.copy(error = MR.strings.screen_login_wrong_credentials) } }
        }
    }

    suspend fun onAuthOtpClicked() {
        coroutineScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val secret = Regex("secret=([^&]+)").find(uiState.value.otpCode)?.groupValues?.get(1)

            secret.toResultOr { }
                .flatMap {
                    loginUseCase.authWithOtpSecret(it)
                }
                .also {
                    _uiState.update { it.copy(isLoading = false) }
                }
                .onSuccess { status ->
                    _uiState.update { it.copy(error = null) }
                    when(status){
                        LoginUseCase.LoginStatus.NEED_CREDENTIALS -> onNeedPassword()
                        LoginUseCase.LoginStatus.NEED_MFA -> onNeedOtp()
                        LoginUseCase.LoginStatus.SUCCESS -> onLoggedIn(secret)
                    }
                }.onFailure { _uiState.update { it.copy(error = MR.strings.screen_login_wrong_otp_secret) } }
        }
    }

    private fun onLoggedIn(totpSecret:String? = null){
        loginUseCase.saveCredential(Credential(uiState.value.userId,uiState.value.password,totpSecret))
        onLoggedIn.invoke()
    }
}