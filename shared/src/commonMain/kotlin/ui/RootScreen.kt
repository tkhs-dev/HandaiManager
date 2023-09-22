package ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.screen.launch.LaunchScreen

@Composable
fun RootScreen() {
    Navigator(LaunchScreen)
}