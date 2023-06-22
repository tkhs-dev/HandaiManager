package ui.page.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val viewModel = object : KoinComponent {
        val viewModel: LoginScreenViewModel by inject()
    }.viewModel

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        TextField(
            value = uiState.userId,
            label = { Text("UserID") },
            onValueChange = { viewModel.onUserIdChanged(it) },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
        )
        TextField(value = "Password", onValueChange = { })
        Button(onClick = { }, modifier = Modifier.width(280.dp)) {
            Text("ログイン")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(uiState:LoginScreenViewModel.UiState,viewModel: LoginScreenViewModel){

}