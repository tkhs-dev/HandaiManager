package di

import domain.repository.CleRepository
import domain.repository.ConfigRepository
import domain.repository.CredentialRepository
import domain.repository.IdpRepository
import domain.repository.KoanRepository
import domain.repository.LicensesRepository
import domain.usecase.LoginUseCase
import domain.usecase.PreferenceUsecase
import org.koin.dsl.module
import ui.screen.home.HomeScreenModel
import ui.screen.launch.LaunchScreenModel
import ui.screen.license.LicenseScreenModel
import ui.screen.login.LoginScreenModel
import util.FileCookiesStorage

val commonModule = module{
    single { FileCookiesStorage() }
    single{IdpRepository(fileCookiesStorage = get())}
    single{CleRepository(fileCookiesStorage = get())}
    single{ KoanRepository(fileCookiesStorage = get())}
    single { CredentialRepository() }
    single { ConfigRepository() }
    single { LicensesRepository() }
    single {LoginUseCase(get(),get(),get(),get())}
    single { PreferenceUsecase(get()) }
    factory { LaunchScreenModel(get(), get()) }
    factory { LoginScreenModel(get())}
    factory { HomeScreenModel()}
    factory { LicenseScreenModel(get()) }
}