package di

import domain.repository.CleApiRepository
import domain.repository.ConfigRepository
import domain.repository.CredentialRepository
import domain.repository.IdpRepository
import domain.usecase.LoginUseCase
import domain.usecase.PreferenceUsecase
import org.koin.dsl.module
import ui.screen.home.HomeScreenViewModel
import ui.screen.login.LoginScreenViewModel
import util.FileCookiesStorage

val commonModule = module{
    single { FileCookiesStorage() }
    single{IdpRepository(fileCookiesStorage = get())}
    single{CleApiRepository(fileCookiesStorage = get())}
    single { CredentialRepository() }
    single { ConfigRepository() }
    single {LoginUseCase(get(),get(),get())}
    single { PreferenceUsecase(get()) }
    factory { LoginScreenViewModel(get())}
    factory { HomeScreenViewModel()}
}