package com.example.aplicacionmovilinesmr.ui.screens.configscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.UserManager
import com.example.aplicacionmovilinesmr.domain.usecases.DarBajaUserUseCase
import com.example.aplicacionmovilinesmr.domain.usecases.DeleteUserProfileUseCase
import com.example.aplicacionmovilinesmr.domain.usecases.GetUserProfileUseCase
import com.example.aplicacionmovilinesmr.domain.usecases.LogoutUseCase
import com.example.aplicacionmovilinesmr.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val deleteUserProfileUseCase: DeleteUserProfileUseCase,
    private val darBajaUserUseCase: DarBajaUserUseCase,
    private val userManager: UserManager,
) : ViewModel(){

    private val _uiState: MutableStateFlow<ConfigState> by lazy {
        MutableStateFlow(ConfigState())
    }
    val uiState: StateFlow<ConfigState> = _uiState

    init {
        _uiState.value = ConfigState(
            error = null,
            loading = false,
        )
    }

    fun handleEvent(event: ConfigEvent){
        when(event){
            ConfigEvent.DoLogout -> logout()
            ConfigEvent.DoBaja -> baja()
            ConfigEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase.logoutUser()
        }
    }

    private fun baja(){
        val idUser = runBlocking { userManager.getIdUser().first()?.toInt() }
        viewModelScope.launch {
            val userProfile = idUser?.let { getUserProfileUseCase.invoke(it).first() }
            if (userProfile != null){
                darBajaUserUseCase.invoke(userProfile.email)
                    .collect{res ->
                        when(res){
                            is NetworkResult.Error -> _uiState.update {

                                it.copy(
                                    error = res.message,
                                    loading = false,
                                )}
                            is NetworkResult.Loading -> _uiState.update {
                                it.copy(loading = false)
                            }
                            is NetworkResult.Success -> {
                                deleteUserProfileUseCase.invoke(userProfile)
                                logout()
                            }
                        }

                    }
            }
        }
    }

}