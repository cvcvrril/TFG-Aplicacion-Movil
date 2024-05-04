package com.example.aplicacionmovilinesmr.domain.modelo

import com.example.aplicacionmovilinesmr.data.modelo.request.CredentialRequest
import com.example.aprobarines.data.modelo.response.CredentialResponse
import java.time.LocalDate

data class Credential(
    val id: Int = 0,
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val nombreCompleto: String = "",
    var fechaNacimiento : String = "",
) {
    fun toCredentialResponse(): CredentialResponse = CredentialResponse(id, username, email)
    fun toCredentialRequest(): CredentialRequest = CredentialRequest(id, username, password, email, nombreCompleto, fechaNacimiento)
    //fun toCredentialRequest(): CredentialRequest = CredentialRequest(id, username, password, email, nombreCompleto, )
}