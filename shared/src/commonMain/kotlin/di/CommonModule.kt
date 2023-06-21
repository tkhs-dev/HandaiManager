package di

import network.Idp
import network.IdpService
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module{
    singleOf(::Idp)
}