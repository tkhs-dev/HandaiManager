
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.AppModule
import io.kanro.compose.jetbrains.expui.theme.LightTheme
import io.kanro.compose.jetbrains.expui.window.JBWindow
import org.koin.core.context.GlobalContext.startKoin
import java.awt.Dimension

fun main() = application {
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
        window.minimumSize = Dimension(800, 450)
        MaterialTheme {
            MainView()
        }
    }
}
