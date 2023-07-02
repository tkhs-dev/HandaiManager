package ui.screen.home

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed class HomeScreenPage: Parcelable {
    object Dashboard: HomeScreenPage()
    object Activity: HomeScreenPage()
    object Settings: HomeScreenPage()
}