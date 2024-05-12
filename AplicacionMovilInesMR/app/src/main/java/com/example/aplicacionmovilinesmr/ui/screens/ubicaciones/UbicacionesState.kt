package com.example.aplicacionmovilinesmr.ui.screens.ubicaciones

import com.example.aplicacionmovilinesmr.domain.modelo.Ubi
import com.example.aplicacionmovilinesmr.domain.modelo.dto.UbiDTO

data class UbicacionesState(
    val ubicaciones :List<UbiDTO> = emptyList(),
    val error: String? = null,
    val loading: Boolean = false,
)