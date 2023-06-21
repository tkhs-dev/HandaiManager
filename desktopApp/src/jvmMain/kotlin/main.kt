import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.github.xxfast.decompose.LocalComponentContext
import ui.page.login.LoginScreen

fun main() = application {
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    Window(onCloseRequest = ::exitApplication) {
        CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
            MaterialTheme {
                MainView()
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    LoginScreen()
}
