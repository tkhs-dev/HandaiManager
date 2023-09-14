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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.stringResource
import dev.tkhs.handaimanager.MR
import model.Licenses
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseScreen(onBackClicked: () -> Unit = {}) {
    val viewModel = remember{ object : KoinComponent {
        val viewModel: LicenseScreenViewModel by inject() }
    }.viewModel

    val licenses by viewModel.licenses.collectAsState()

    LaunchedEffect(Unit){
        viewModel.onLaunched()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(MR.strings.oss_license)) },
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
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