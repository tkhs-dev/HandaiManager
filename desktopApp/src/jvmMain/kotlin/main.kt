import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.AppModule
import io.github.xxfast.decompose.LocalComponentContext
import org.koin.core.context.GlobalContext.startKoin
import ui.page.login.LoginScreen

fun main() = application {
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    startKoin(){
        modules(AppModule().appModule())
    }

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
