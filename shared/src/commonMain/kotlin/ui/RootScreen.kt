package ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter
import ui.screen.home.HomeScreen
import ui.screen.home.HomeScreenPage
import ui.screen.launch.LaunchScreen
import ui.screen.license.LicenseScreen
import ui.screen.login.LoginScreen
import ui.screen.login.LoginScreenPage
import ui.screen.preference.PreferenceScreen

@Composable
fun RootScreen() {
    val router: Router<RootStateModel> = rememberRouter(RootStateModel::class,listOf(RootStateModel.Launch))
    val router_home: Router<HomeScreenPage> = rememberRouter(HomeScreenPage::class,listOf(HomeScreenPage.Dashboard))
    val router_login: Router<LoginScreenPage> = rememberRouter(LoginScreenPage::class,listOf(LoginScreenPage.Information))
    RoutedContent(
    router = router,
    animation = stackAnimation(fade()),
    ) { screen ->
        when (screen) {
            RootStateModel.Launch ->
                LaunchScreen({router.push(RootStateModel.Login)},{router.push(RootStateModel.Home)})
            RootStateModel.Home ->
                HomeScreen(router_home,{router.push(RootStateModel.Preference)})
            RootStateModel.Login ->
                LoginScreen(router_login,{router.push(RootStateModel.Home)})
            RootStateModel.Preference ->
                PreferenceScreen({router.push(RootStateModel.License)})

            RootStateModel.License ->
                LicenseScreen({router.pop()})
        }
    }
}