package com.example.aplicacionmovilinesmr.ui.screens.configscreen

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionmovilinesmr.domain.usecases.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
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
            ConfigEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
            is ConfigEvent.OnTemaChange -> {
                val newTheme = if (event.tema) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
                AppCompatDelegate.setDefaultNightMode(newTheme)
                _uiState.update { it.copy(tema = event.tema) }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase.logoutUser()
        }
    }

}