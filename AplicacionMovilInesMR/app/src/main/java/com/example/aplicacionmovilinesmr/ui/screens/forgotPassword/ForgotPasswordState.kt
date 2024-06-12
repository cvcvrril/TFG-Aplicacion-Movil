package com.example.aplicacionmovilinesmr.ui.screens.forgotPassword

data class ForgotPasswordState (
    val error: String? = null,
    val loading: Boolean = false,
    val email: String = "",
)