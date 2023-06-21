package ui

import com.arkivanov.essenty.parcelable.Parcelable

sealed class Screen:Parcelable{
    object Home:Screen()
    object Login:Screen()
}
