package di

import network.Idp
import network.IdpService
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.page.login.LoginScreenViewModel

val commonModule = module{
    singleOf(::Idp)
    factory { LoginScreenViewModel()}
}