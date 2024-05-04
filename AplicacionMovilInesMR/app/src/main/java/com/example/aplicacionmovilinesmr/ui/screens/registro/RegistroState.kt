package com.example.aplicacionmovilinesmr.ui.screens.registro

import com.example.aplicacionmovilinesmr.domain.modelo.Credential

data class RegistroState(
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val nombreCompleto: String? = null,
    val newCredential: Credential = Credential(),
    val error: String? = null,
    val loading: Boolean = false,
)