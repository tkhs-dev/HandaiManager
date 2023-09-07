import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.screen.RootScreen

@Composable
fun App() {
    MaterialTheme {
        RootScreen()
    }
}

expect fun getPlatformName(): String