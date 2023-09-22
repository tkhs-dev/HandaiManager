package ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ui.screen.home.page.Dashboard
import ui.screen.license.LicenseScreen
import ui.screen.preference.PreferenceScreen

object HomeScreen: Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TabNavigator(Dashboard){
            Row{
                NavigationRail(modifier = Modifier.fillMaxHeight()) {
                    NavigationRailItem(icon = { Icon(Icons.Rounded.Home,"Dashboard") }, selected = true, onClick = {  })
                    Box(modifier = Modifier.weight(1f))
                    NavigationRailItem(icon = { Icon(Icons.Outlined.Settings,"Settings") }, selected = false, onClick = { navigator.push(
                        PreferenceScreen { navigator.push(LicenseScreen) }) })
                }
                Box(){
                    CurrentTab()
                }

            }
        }
    }

}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen(router: Router<HomeScreenPage>,onNavigateToPreference: () -> Unit = {  }) {
//    val viewModel = remember{ object : KoinComponent {
//        val viewModel: HomeScreenViewModel by inject() }
//    }.viewModel
//    val uiState by viewModel.uiState.collectAsState()
//
//    val mainPageContent = setOf(
//        PageContent(Icons.Rounded.Home,"Dashboard",HomeScreenPage.Dashboard),
//        PageContent(Icons.Outlined.DateRange,"Activity",HomeScreenPage.Activity),
//        //PageContent(Icons.Outlined.Notifications,"Notifications",HomeScreenPage.Notifications),
//    )
//    val preferencePageContent = setOf(
//        PageContent(Icons.Outlined.Settings,"Settings",HomeScreenPage.Settings),
//    )
//
//    val colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = Color(46, 83, 218), unselectedContainerColor = Color.Transparent, selectedTextColor = Color.White,unselectedTextColor = Color.DarkGray, selectedIconColor = Color.White, unselectedIconColor = Color.Gray)
//    val modifier = Modifier.padding(10.dp,10.dp).height(45.dp)
//    Row(modifier = Modifier.background(color = Color(249, 241, 240)).fillMaxWidth().fillMaxHeight()) {
//        PermanentNavigationDrawer(drawerContent = {
//            PermanentDrawerSheet(drawerContainerColor = Color.White) {
//                Text("MAIN MENU", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(10.dp,10.dp))
//                for (page in mainPageContent){
//                    NavigationDrawerItem(icon={Icon(page.icon,page.text)}, label = { Text(page.text) }, selected = uiState.selectedPage == page.page, onClick = {
//                        viewModel.onNavigationDrawerClicked(page.page)
//                        router.bringToFront(page.page)
//                    },shape = RoundedCornerShape(10.dp), modifier = modifier, colors = colors)
//                }
//                Divider(modifier = Modifier.padding(10.dp,10.dp))
//                Text("PREFERENCE", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(10.dp,10.dp))
//                for (page in preferencePageContent){
//                    NavigationDrawerItem(icon={Icon(page.icon,page.text)}, label = { Text(page.text) }, selected = uiState.selectedPage == page.page, onClick = {
//                        viewModel.onNavigationDrawerClicked(page.page)
//                        onNavigateToPreference()
//                    },shape = RoundedCornerShape(10.dp), modifier = modifier, colors = colors)                }
//            }
//        },
//        modifier = Modifier.width(220.dp)
//        ){}
//        Divider(modifier = Modifier.fillMaxHeight().width(1.dp), color = Color.LightGray)
//        RoutedContent(
//            router = router,
//            animation = stackAnimation(fade()),
//        ) { screen ->
//            when (screen) {
//                HomeScreenPage.Dashboard->
//                    Dashboard()
//                HomeScreenPage.Settings->
//                    Settings()
//                HomeScreenPage.Activity->
//                    Activity()
//            }
//        }
//    }
//
//}
//
//private data class PageContent(val icon:ImageVector,val text:String,val page:HomeScreenPage)