package com.example.aplicacionmovilinesmr.data.modelo.request

data class NewUbiDTO(
    val long: Double,
    val lat: Double,
    val idUser: Int = 0,
) {
}