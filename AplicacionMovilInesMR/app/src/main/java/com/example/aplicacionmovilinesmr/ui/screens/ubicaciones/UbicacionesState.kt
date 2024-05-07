package com.example.aplicacionmovilinesmr.ui.screens.ubicaciones

import com.example.aplicacionmovilinesmr.domain.modelo.Ubi

data class UbicacionesState(
    val ubicaciones :List<Ubi> = emptyList(),
    val error: String? = null,
    val loading: Boolean = false,
)