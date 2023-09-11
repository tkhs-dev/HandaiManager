import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import ui.RootScreen

@Composable
fun App() {
    MaterialTheme {
        RootScreen()
    }
}

expect fun getPlatformName(): String