package ui

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed class RootStateModel:Parcelable{
    object Home:RootStateModel()
    object Login:RootStateModel()
    object Preference:RootStateModel()
}
