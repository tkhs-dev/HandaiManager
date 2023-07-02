package ui.screen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter
import ui.Screen
import ui.screen.home.HomeScreen
import ui.screen.login.LoginScreen

@Composable
fun RootScreen() {
    val router: Router<Screen> = rememberRouter(Screen::class,listOf(Screen.Home))
    RoutedContent(
    router = router,
    animation = stackAnimation(fade()),
    ) { screen ->
        when (screen) {
            Screen.Home ->
                HomeScreen()
            Screen.Login ->
                LoginScreen()
        }
    }
}