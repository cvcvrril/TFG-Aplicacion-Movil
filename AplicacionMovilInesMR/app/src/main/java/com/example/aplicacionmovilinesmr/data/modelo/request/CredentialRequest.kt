package com.example.aplicacionmovilinesmr.data.modelo.request

import java.time.LocalDate

data class CredentialRequest(
    val id: Int = 0,
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val nombreCompleto: String = "",
    val fechaNacimiento : String = "",

)