package com.example.aplicacionmovilinesmr.ui.screens.perfil

import com.example.aplicacionmovilinesmr.domain.modelo.Profile

data class PerfilState (
    val perfil : Profile? = null,
    val error: String? = null,
    val loading: Boolean = false,
)