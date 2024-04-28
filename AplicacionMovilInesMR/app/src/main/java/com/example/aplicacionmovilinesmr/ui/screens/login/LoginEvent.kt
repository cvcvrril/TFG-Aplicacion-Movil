package com.example.aplicacionmovilinesmr.ui.screens.login

sealed class LoginEvent {
    class Login () : LoginEvent()
    class OnUserNameChange(val username: String) : LoginEvent()
    class OnPasswordChange(val password: String) : LoginEvent()
    object ErrorVisto : LoginEvent()

}