package ui.screen.license

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import dev.tkhs.handaimanager.MR
import model.Licenses

object LicenseScreen: Screen{
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<LicenseScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        val licenses by viewModel.licenses.collectAsState()

        LaunchedEffect(Unit){
            viewModel.onLaunched()
        }

        Column(modifier = Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(MR.strings.oss_license)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
            )
            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
                if(licenses == null){
                    CircularProgressIndicator()
                }else{
                    (licenses!!).licenses.values.forEach {
                        LicenseGroup(it, (licenses!!).libraries.filter { library -> library.licenses.contains(it.hash) })
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun LicenseGroup(license: Licenses.License, libraries: List<Licenses.Library>){
    Column(modifier = Modifier.padding(0.dp, 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){
        Text(text = license.name, style = TextStyle(fontSize = 20.sp))
        Column(modifier = Modifier.padding(start = 16.dp)) {
            for (library in libraries) {
                Text(text = "・${library.uniqueId}:${library.artifactVersion}")
            }
        }
        Text(text = license.content ?: license.url)
    }
}