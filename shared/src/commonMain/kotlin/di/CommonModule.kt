package di

import data.cache.CacheManager
import data.cache.CacheManagerImpl
import data.realm.RealmManager
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
import ui.screen.home.page.DashboardScreenModel
import ui.screen.launch.LaunchScreenModel
import ui.screen.license.LicenseScreenModel
import ui.screen.login.LoginScreenModel
import util.FileCookiesStorage

val commonModule = module{
    single { RealmManager() }
    single { CacheManagerImpl(get()) as CacheManager }
    single { FileCookiesStorage() }
    single{IdpRepository(cacheManager = get(), fileCookiesStorage = get())}
    single{CleRepository(cacheManager = get(), fileCookiesStorage = get())}
    single{ KoanRepository(cacheManager = get(), fileCookiesStorage = get())}
    single { CredentialRepository() }
    single { ConfigRepository() }
    single { LicensesRepository() }
    single {LoginUseCase(get(),get(),get(),get())}
    single { PreferenceUsecase(get(), get()) }
    factory { LaunchScreenModel(get(), get(), get(), get()) }
    factory { LoginScreenModel(get())}
    factory { HomeScreenModel()}
    factory { DashboardScreenModel(get()) }
    factory { LicenseScreenModel(get()) }
}