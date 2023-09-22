package ui.screen.launch

import cafe.adriel.voyager.core.model.ScreenModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import domain.usecase.LoginUseCase
import util.FileCookiesStorage
import util.Logger

class LaunchScreenModel(private val fileCookiesStorage: FileCookiesStorage, private val loginUseCase: LoginUseCase) : ScreenModel {
    private var onNavigateToLogin: () -> Unit = {}
    private var onNavigateToHome: () -> Unit = {}

    fun setListeners(onNavigateToLogin: () -> Unit, onNavigateToHome: () -> Unit){
        this.onNavigateToLogin = onNavigateToLogin
        this.onNavigateToHome = onNavigateToHome
    }

    suspend fun onLaunched(){
        Logger.debug(this::class.simpleName, "onLaunched")
        fileCookiesStorage.loadCookies()
        loginUseCase.loginCleWithSavedCredential()
            .onFailure {
                Logger.info(this::class.simpleName, "login failed: navigate to login screen")
                onNavigateToLogin()
            }.onSuccess {
                Logger.info(this::class.simpleName, "login success: navigate to home screen")
                onNavigateToHome()
            }
    }
}