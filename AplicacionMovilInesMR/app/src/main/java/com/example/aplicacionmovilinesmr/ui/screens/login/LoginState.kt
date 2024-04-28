package com.example.aplicacionmovilinesmr.ui.screens.login

data class LoginState(
    val error: String? = null,
    val login: Boolean = false,
    val username: String? = null,
    val password: String? = null,
    val loading: Boolean = false
)