package ui.screen.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Composable
actual fun PreferenceScreen() {
    val viewModel = remember{ object : KoinComponent {
        val viewModel: PreferenceScreenViewModel by inject() }
    }.viewModel
}