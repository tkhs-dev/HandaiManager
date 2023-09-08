package ui

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed class Screen:Parcelable{
    object Home:Screen()
    object Login:Screen()
    object Preference:Screen()
}
