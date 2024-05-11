package com.example.aplicacionmovilinesmr.ui.screens.perfil

import androidx.lifecycle.ViewModel
import com.example.aplicacionmovilinesmr.domain.usecases.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel(){

    private val _uiState: MutableStateFlow<PerfilState> by lazy {
        MutableStateFlow(PerfilState())
    }
    val uiState: StateFlow<PerfilState> = _uiState


}