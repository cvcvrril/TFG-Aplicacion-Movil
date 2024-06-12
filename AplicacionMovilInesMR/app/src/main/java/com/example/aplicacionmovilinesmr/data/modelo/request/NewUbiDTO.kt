package com.example.aplicacionmovilinesmr.data.modelo.request

data class NewUbiDTO(
    val lat: Double,
    val lon: Double,
    val idUser: Int = 0,
) {
}