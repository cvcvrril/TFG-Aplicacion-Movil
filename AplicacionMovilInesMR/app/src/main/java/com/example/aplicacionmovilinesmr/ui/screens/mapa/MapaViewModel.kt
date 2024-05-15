package com.example.aplicacionmovilinesmr.ui.screens.mapa

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MapaViewModel @Inject constructor(
) : ViewModel(){

    private val _uiState: MutableStateFlow<MapaState> by lazy {
        MutableStateFlow(MapaState())
    }
    val uiState: StateFlow<MapaState> = _uiState


}