package ui.screen.home.page

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object Dashboard: Tab {
    @Composable
    override fun Content() {
        Text("Dashboard")
    }

    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = 0u,
                title = "Dashboard",
                icon = null
            )
        }
}