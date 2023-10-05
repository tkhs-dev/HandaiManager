package ui.screen.launch

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import data.realm.RealmManager
import domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import util.FileCookiesStorage
import util.Logger

class LaunchScreenModel(private val fileCookiesStorage: FileCookiesStorage,private val realmManager: RealmManager, private val loginUseCase: LoginUseCase) : ScreenModel {
    private var onNavigateToLogin: () -> Unit = {}
    private var onNavigateToHome: () -> Unit = {}

    fun setListeners(onNavigateToLogin: () -> Unit, onNavigateToHome: () -> Unit){
        this.onNavigateToLogin = onNavigateToLogin
        this.onNavigateToHome = onNavigateToHome
    }

    suspend fun onLaunched(){
        coroutineScope.launch {
            Logger.debug(this::class.simpleName, "onLaunched")
            fileCookiesStorage.loadCookies()
            realmManager.open()
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
}