package ui.screen.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Composable
actual fun PreferenceScreen() {
    val viewModel = remember{ object : KoinComponent {
        val viewModel: PreferenceScreenViewModel by inject() }
    }.viewModel

    val config by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.widthIn(400.dp,600.dp).padding(10.dp,0.dp), verticalArrangement = Arrangement.spacedBy(20.dp)){
        ConfigSection(title = "一般"){
        }
        Divider()
        ConfigSection(title = "デバッグ"){
            versionInfo()
        }
    }
}