package ui.screen.home.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.tkhs.handaimanager.MR
import model.User

object Dashboard: Tab {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<DashboardScreenModel>()

        val user by screenModel.user.collectAsState()

        LaunchedEffect(Unit){
            screenModel.init()
        }

        //Text("Dashboard")
        user(user)
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

    @Composable
    fun user(user: User?) {
        if(user != null) {
            Column(modifier = Modifier) {
                Text(user.name.given, style = TextStyle(fontSize = 20.sp))
                Text(user.contact.email, style = TextStyle(fontSize = 14.sp, fontFamily = fontFamilyResource(MR.fonts.Inter.regular)))
            }
        }else{

        }
    }
}