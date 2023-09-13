package ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.push
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter
import ui.screen.home.HomeScreen
import ui.screen.launch.LaunchScreen
import ui.screen.login.LoginScreen
import ui.screen.preference.PreferenceScreen

@Composable
fun RootScreen() {
    val router: Router<RootStateModel> = rememberRouter(RootStateModel::class,listOf(RootStateModel.Launch))
    RoutedContent(
    router = router,
    animation = stackAnimation(fade()),
    ) { screen ->
        when (screen) {
            RootStateModel.Launch ->
                LaunchScreen({router.push(RootStateModel.Login)},{router.push(RootStateModel.Home)})
            RootStateModel.Home ->
                HomeScreen()
            RootStateModel.Login ->
                LoginScreen()
            RootStateModel.Preference ->
                PreferenceScreen()
        }
    }
}