package di

import org.koin.dsl.module
import ui.screen.preference.PreferenceScreenDesktopViewModel

actual val platformModule = module {
    factory { PreferenceScreenDesktopViewModel(get()) }
}