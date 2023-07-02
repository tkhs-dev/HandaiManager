package ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.bringToFront
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.getAesKey
import platform.saveFile
import platform.saveFileEncrypted
import ui.screen.home.page.Activity
import ui.screen.home.page.Dashboard
import ui.screen.home.page.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    saveFile("aaa.txt","Hello World".encodeToByteArray())
    saveFileEncrypted("bbb.txt","Hello World".encodeToByteArray(), getAesKey())

    val viewModel = remember{ object : KoinComponent {
        val viewModel: HomeScreenViewModel by inject() }
    }.viewModel
    val uiState = viewModel.uiState.collectAsState()

    val router: Router<HomeScreenPage> = rememberRouter(HomeScreenPage::class,listOf(HomeScreenPage.Dashboard))

    val mainPageContent = setOf<PageContent>(
        PageContent(Icons.Rounded.Home,"Dashboard",HomeScreenPage.Dashboard),
        PageContent(Icons.Outlined.DateRange,"Activity",HomeScreenPage.Activity),
        //PageContent(Icons.Outlined.Notifications,"Notifications",HomeScreenPage.Notifications),
    )
    val preferencePageContent = setOf<PageContent>(
        PageContent(Icons.Outlined.Settings,"Settings",HomeScreenPage.Settings),
    )

    val colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = Color(46, 83, 218), unselectedContainerColor = Color.Transparent, selectedTextColor = Color.White,unselectedTextColor = Color.DarkGray, selectedIconColor = Color.White, unselectedIconColor = Color.Gray)
    val modifier = Modifier.padding(10.dp,10.dp).height(45.dp)
    Row(modifier = Modifier.background(color = Color(249, 241, 240)).fillMaxWidth().fillMaxHeight()) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet(drawerContainerColor = Color.White) {
                Text("MAIN MENU", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(10.dp,10.dp))
                for (page in mainPageContent){
                    NavigationDrawerItem(icon={Icon(page.icon,page.text)}, label = { Text(page.text) }, selected = uiState.value.selectedPage == page.page, onClick = {
                        viewModel.onNavigationDrawerClicked(page.page)
                        router.bringToFront(page.page)
                    },shape = RoundedCornerShape(10.dp), modifier = modifier, colors = colors)
                }
                Divider(modifier = Modifier.padding(10.dp,10.dp))
                Text("PREFERENCE", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(10.dp,10.dp))
                for (page in preferencePageContent){
                    NavigationDrawerItem(icon={Icon(page.icon,page.text)}, label = { Text(page.text) }, selected = uiState.value.selectedPage == page.page, onClick = {
                        viewModel.onNavigationDrawerClicked(page.page)
                        router.bringToFront(page.page)
                    },shape = RoundedCornerShape(10.dp), modifier = modifier, colors = colors)                }
            }
        },
        modifier = Modifier.width(220.dp)
        ){}
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp), color = Color.LightGray)
        RoutedContent(
            router = router,
            animation = stackAnimation(fade()),
        ) { screen ->
            when (screen) {
                HomeScreenPage.Dashboard->
                    Dashboard()
                HomeScreenPage.Settings->
                    Settings()
                HomeScreenPage.Activity->
                    Activity()
            }
        }
    }

}

private data class PageContent(val icon:ImageVector,val text:String,val page:HomeScreenPage)