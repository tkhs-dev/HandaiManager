package ui.screen.preference

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
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
        val viewModel: PreferenceScreenDesktopViewModel by inject() }
    }.viewModel

    val uiState by viewModel.uiState.collectAsState()

    Row{
        Box(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.widthIn(400.dp,600.dp)) {
        }
        Box(modifier = Modifier.weight(1f))
    }
}