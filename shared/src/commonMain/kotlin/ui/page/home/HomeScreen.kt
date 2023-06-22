package ui.page.home

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.push
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter
import ui.Screen
import ui.page.login.LoginScreen

@Composable
fun HomeScreen() {
    val router: Router<Screen> = rememberRouter(Screen::class,listOf(Screen.Home))
    RoutedContent(
        router = router,
        animation = stackAnimation(com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade()),
    ) { screen ->
        when (screen) {
            Screen.Home ->
                Button({router.push(Screen.Login)}) {
                    Text("Home")
                }

            Screen.Login ->
                LoginScreen()
        }
    }
}