package di

import org.koin.dsl.module
import ui.screen.preference.PreferenceScreenViewModel

actual val platformModule = module {
    factory { PreferenceScreenViewModel(get()) }
}