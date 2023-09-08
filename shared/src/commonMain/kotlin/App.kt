import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import ui.screen.RootScreen

@Composable
fun App() {
    MaterialTheme {
        RootScreen()
    }
}

expect fun getPlatformName(): String