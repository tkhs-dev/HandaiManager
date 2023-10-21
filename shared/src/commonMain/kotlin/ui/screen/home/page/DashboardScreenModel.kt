package ui.screen.home.page

import cafe.adriel.voyager.core.model.ScreenModel
import com.github.michaelbull.result.getOr
import domain.repository.CleRepository
import domain.repository.KoanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.User

class DashboardScreenModel(private val cleRepository: CleRepository, private val koanRepository: KoanRepository) : ScreenModel {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    suspend fun init(){
        _user.value = cleRepository.getUserInfo().getOr(null)
    }
}