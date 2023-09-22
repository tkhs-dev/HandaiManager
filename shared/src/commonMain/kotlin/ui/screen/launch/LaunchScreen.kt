package ui.screen.launch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.screen.home.HomeScreen
import ui.screen.login.LoginScreen

object LaunchScreen: Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<LaunchScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        screenModel.setListeners({navigator.popUntilRoot(); navigator.replace(LoginScreen{it.popUntilRoot(); it.replace(HomeScreen)})}, {navigator.popUntilRoot(); navigator.replace(HomeScreen)})

        LaunchedEffect(Unit){
            screenModel.onLaunched()
        }

        Row(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.Center,verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator()
        }
    }
}