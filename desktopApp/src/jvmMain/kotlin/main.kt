
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.AppModule
import io.github.xxfast.decompose.LocalComponentContext
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import io.kanro.compose.jetbrains.expui.window.JBWindow
import org.koin.core.context.GlobalContext.startKoin

fun main() = application {
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    startKoin {
        modules(AppModule().appModule())
    }

    JBWindow(
        icon = null,
        title = "阪大 CLE+",
        showTitle = true,
        theme = LightTheme,
        state = rememberWindowState(size = DpSize(900.dp, 700.dp)),
        onCloseRequest = ::exitApplication,
        mainToolBar = {
            Row(
                Modifier.mainToolBarItem(
                    Alignment.End,
                    true
                )
            ) {
            }
        }) {
        CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
            MaterialTheme {
                MainView()
            }
        }
    }
}
