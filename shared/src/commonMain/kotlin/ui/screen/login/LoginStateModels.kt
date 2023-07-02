package ui.screen.login

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed class LoginScreenPage: Parcelable {
    object Information: LoginScreenPage()
    object AuthPassword: LoginScreenPage()
    object AuthOtp: LoginScreenPage()
    object LoginResultPage: LoginScreenPage()
}