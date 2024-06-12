package com.example.aplicacionmovilinesmr.ui.screens.forgotPassword

sealed class ForgotPasswordEvent {

    object ErrorVisto : ForgotPasswordEvent()
    class OnEmailChange(val email: String): ForgotPasswordEvent()
    class ForgotPassword() : ForgotPasswordEvent()
}