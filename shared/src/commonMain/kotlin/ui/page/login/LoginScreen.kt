package ui.page.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.push
import io.github.xxfast.decompose.router.Router
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Composable
fun LoginScreen() {
    val viewModel = remember{ object : KoinComponent {
        val viewModel: LoginScreenViewModel by inject() }
    }.viewModel

    val uiState by viewModel.uiState.collectAsState()

    val router: Router<LoginFlowScreen> = rememberRouter(LoginFlowScreen::class,listOf(LoginFlowScreen.Information))

    viewModel.setListeners(
        onLoggedIn = {router.push(LoginFlowScreen.LoginResult)},
        onNeedPassword = {router.push(LoginFlowScreen.AuthPassword)},
        onNeedOtp = {router.push(LoginFlowScreen.AuthOtp)}
    )

    val scope = rememberCoroutineScope()
    RoutedContent(
        router = router,
        animation = stackAnimation(slide()),
    ) { screen ->
        when (screen) {
            LoginFlowScreen.Information ->
                Information(viewModel) {
                    scope.launch {
                        viewModel.onStartLoginClicked()
                    }
                }

            LoginFlowScreen.AuthPassword ->
                AuthPassword(viewModel){
                    scope.launch {
                        viewModel.onAuthPasswordClicked()
                    }
                }

            LoginFlowScreen.AuthOtp ->
                AuthOtp(viewModel){
                    scope.launch {
                        viewModel.onAuthOtpClicked()
                    }
                }

            LoginFlowScreen.LoginResult ->
                LoginResult()
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Information(viewModel: LoginScreenViewModel,onNext:()->Unit){
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        TextField(
            value = uiState.userId,
            label = { Text("説明文") },
            onValueChange = { viewModel.onUserIdChanged(it) },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            singleLine = true
        )
        Button(onClick = { onNext() }, modifier = Modifier.width(280.dp),enabled = !uiState.isLoading) {
            if(!uiState.isLoading)Text("次へ")
            else CircularProgressIndicator(modifier =  Modifier.size(20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthPassword(viewModel: LoginScreenViewModel,onNext:()->Unit){
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        TextField(
            value = uiState.userId,
            label = { Text("UserID") },
            isError = uiState.error.isNotEmpty(),
            onValueChange = { viewModel.onUserIdChanged(it) },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            singleLine = true
        )
        TextField(
            value = uiState.password,
            label = {Text("Password")},
            isError = uiState.error.isNotEmpty(),
            supportingText = { if(uiState.error.isNotEmpty()) Text(uiState.error) else null },
            onValueChange = { viewModel.onPasswordChanged(it) },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
        )
        Button(onClick = { onNext() }, modifier = Modifier.width(280.dp),enabled = !uiState.isLoading) {
            if(!uiState.isLoading)Text("次へ")
            else CircularProgressIndicator(modifier =  Modifier.size(20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthOtp(viewModel: LoginScreenViewModel,onNext:()->Unit){
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        TextField(
            value = uiState.otpCode,
            label = { Text("ワンタイムパスワード") },
            isError = uiState.error.isNotEmpty(),
            supportingText = { if(uiState.error.isNotEmpty()) Text(uiState.error) else null },
            onValueChange = { viewModel.onOtpCodeChanged(it) },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            )
        )
        Button(onClick = { onNext() }, modifier = Modifier.width(280.dp),enabled = !uiState.isLoading) {
            if(!uiState.isLoading)Text("次へ")
            else CircularProgressIndicator(modifier =  Modifier.size(20.dp))
        }
    }
}

@Composable
fun LoginResult(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Text("ログイン成功")
    }
}