package ui.screen.launch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Composable
fun LaunchScreen(onNavigateToLogin: () -> Unit, onNavigateToHome: () -> Unit) {
    val viewModel = remember{ object : KoinComponent {
        val viewModel: LaunchScreenViewModel by inject() }
    }.viewModel

    viewModel.setListeners(onNavigateToLogin, onNavigateToHome)

    LaunchedEffect(Unit){
        viewModel.onLaunched()
    }

    Row(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.Center,verticalAlignment = Alignment.CenterVertically) {
        CircularProgressIndicator()
    }
}