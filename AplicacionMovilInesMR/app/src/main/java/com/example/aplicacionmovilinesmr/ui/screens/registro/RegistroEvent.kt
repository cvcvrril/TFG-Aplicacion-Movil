package com.example.aplicacionmovilinesmr.ui.screens.registro

sealed class RegistroEvent {

    class Registro () : RegistroEvent()
    class OnUserNameChange(val username: String) : RegistroEvent()
    class OnPasswordChange(val password: String) : RegistroEvent()
    class OnEmailChange(val email: String) : RegistroEvent()
    class OnNombreCompletoChange(val nombreCompleto: String) : RegistroEvent()
    class OnFechaNacimientoChange(val fechaNacimiento: String) : RegistroEvent()
    object ErrorVisto : RegistroEvent()

}