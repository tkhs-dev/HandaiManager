package ui.page.login

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed class LoginFlowScreen: Parcelable {
    object Information: LoginFlowScreen()
    object AuthPassword: LoginFlowScreen()
    object AuthOtp: LoginFlowScreen()
    object LoginResult: LoginFlowScreen()
}