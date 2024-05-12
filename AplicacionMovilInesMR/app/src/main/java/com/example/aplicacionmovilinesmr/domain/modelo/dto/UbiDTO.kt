package com.example.aplicacionmovilinesmr.domain.modelo.dto

import com.example.aplicacionmovilinesmr.domain.modelo.Ubi

data class UbiDTO (
    val id: Int = 0,
    val long: Double = 0.0,
    val lat: Double = 0.0,
    val nombre: String = ""
){

    companion object {
        fun fromUbi(ubi: Ubi): UbiDTO {
            return UbiDTO(
                id = ubi.id,
                long = ubi.long,
                lat = ubi.lat,
                nombre = ""
            )
        }
    }

}
