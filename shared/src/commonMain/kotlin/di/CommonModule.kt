package di

import domain.repository.CleApiRepository
import domain.repository.CredentialRepository
import network.Idp
import network.Cle
import org.koin.dsl.module
import ui.screen.login.LoginScreenViewModel
import domain.usecase.LoginUseCase
import domain.repository.IdpRepository
import ui.screen.home.HomeScreenViewModel

val commonModule = module{
    single{Idp()}
    single{Cle()}
    single{IdpRepository(get())}
    single{CleApiRepository(get())}
    single { CredentialRepository() }
    single {LoginUseCase(get(),get(),get())}
    factory { LoginScreenViewModel(get())}
    factory { HomeScreenViewModel()}
}