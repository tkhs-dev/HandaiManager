package di

import domain.repository.CleApiRepository
import network.Idp
import network.Cle
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.page.login.LoginScreenViewModel
import domain.usecase.LoginUseCase
import domain.repository.IdpRepository

val commonModule = module{
    single{Idp()}
    single{Cle()}
    single{IdpRepository(get())}
    single{CleApiRepository(get())}
    single {LoginUseCase(get(),get())}
    factory { LoginScreenViewModel(get())}
}