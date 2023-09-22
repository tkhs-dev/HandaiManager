package ui.screen.login

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dev.icerock.moko.resources.compose.stringResource
import dev.tkhs.handaimanager.MR
import kotlinx.coroutines.launch

class LoginScreen(private val onLoggedIn: (navigator:Navigator) -> Unit = {}) : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<LoginScreenModel>()
        val scope = rememberCoroutineScope()

        Navigator(Information(screenModel) { scope.launch { screenModel.onStartLoginClicked() } }) {
            screenModel.setListeners(
                onLoggedIn = { onLoggedIn(it) },
                onNeedPassword = { it.push(AuthPassword(screenModel) { scope.launch { screenModel.onAuthPasswordClicked() } }) },
                onNeedOtp = { it.push(AuthOtp(screenModel) { scope.launch { screenModel.onAuthOtpClicked() } }) },
            )
            SlideTransition(it)
        }
    }

    class Information(
        private val screenModel: LoginScreenModel,
        private val onNext: () -> Unit
    ) : Screen {
        @Composable
        override fun Content() {
            val uiState by screenModel.uiState.collectAsState()
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Text(stringResource(MR.strings.screen_login_attention), fontSize = 25.sp)
                Text(
                    stringResource(MR.strings.screen_login_tos),
                    modifier = Modifier.widthIn(0.dp, 400.dp)
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
                        .padding(10.dp),
                    lineHeight = 25.sp
                )
                Button(
                    onClick = { onNext() },
                    modifier = Modifier.width(280.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (!uiState.isLoading) Text(stringResource(MR.strings.screen_login_accept_and_next))
                    else CircularProgressIndicator(modifier = Modifier.size(20.dp))
                }
            }
        }
    }

    class AuthPassword(
        private val screenModel: LoginScreenModel,
        private val onNext: () -> Unit
    ) : Screen {
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        override fun Content() {
            val uiState by screenModel.uiState.collectAsState()
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Text(stringResource(MR.strings.ou_login), fontSize = 25.sp)
                TextField(
                    value = uiState.userId,
                    label = { Text(stringResource(MR.strings.ou_personal_id)) },
                    isError = uiState.error != null,
                    onValueChange = { screenModel.onUserIdChanged(it) },
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                    singleLine = true
                )
                TextField(
                    value = uiState.password,
                    label = { Text(stringResource(MR.strings.ou_password)) },
                    isError = uiState.error != null,
                    supportingText = { if (uiState.error != null) Text(stringResource(uiState.error!!)) else null },
                    onValueChange = { screenModel.onPasswordChanged(it) },
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = { onNext() },
                    modifier = Modifier.width(280.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (!uiState.isLoading) Text(stringResource(MR.strings.next))
                    else CircularProgressIndicator(modifier = Modifier.size(20.dp))
                }
            }
        }
    }

    class AuthOtp(private val screenModel: LoginScreenModel, private val onNext: () -> Unit) :
        Screen {
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        override fun Content() {
            val uiState by screenModel.uiState.collectAsState()
            val text = buildAnnotatedString {
                append(stringResource(MR.strings.screen_login_otp_attention))
                pushStringAnnotation(
                    tag = "URL",
                    annotation = stringResource(MR.strings.screen_login_otp_howto_get_key_url)
                )
                withStyle(
                    SpanStyle(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(MR.strings.screen_login_otp_howto_get_key))
                }
                pop()
            }
            val uriHandler = LocalUriHandler.current

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Text(stringResource(MR.strings.screen_login_otp), fontSize = 25.sp)
                ClickableText(
                    text = text,
                    style = TextStyle(lineHeight = 25.sp),
                    onClick = {
                        val annotation =
                            text.getStringAnnotations(start = it, end = it).firstOrNull()
                        annotation?.let { range ->
                            uriHandler.openUri(range.item)
                        }
                    },
                    modifier = Modifier.widthIn(0.dp, 400.dp)
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
                        .padding(10.dp)
                )
                TextField(
                    value = uiState.otpCode,
                    label = { Text(stringResource(MR.strings.ou_otp)) },
                    isError = uiState.error != null,
                    supportingText = { if (uiState.error != null) Text(stringResource(uiState.error!!)) else null },
                    onValueChange = { screenModel.onOtpCodeChanged(it) },
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    )
                )
                Button(
                    onClick = { onNext() },
                    modifier = Modifier.width(280.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (!uiState.isLoading) Text(stringResource(MR.strings.next))
                    else CircularProgressIndicator(modifier = Modifier.size(20.dp))
                }
            }
        }
    }

    class LoginResult(private val screenModel: LoginScreenModel) : Screen {
        @Composable
        override fun Content() {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Text("ログイン成功")
            }
        }
    }
}