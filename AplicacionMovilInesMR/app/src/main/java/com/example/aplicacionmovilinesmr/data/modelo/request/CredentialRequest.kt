package com.example.aplicacionmovilinesmr.data.modelo.request

data class CredentialRequest(
    val id: Int = 0,
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val nombreCompleto: String = "",
    val fechaNacimiento : String = "",

)