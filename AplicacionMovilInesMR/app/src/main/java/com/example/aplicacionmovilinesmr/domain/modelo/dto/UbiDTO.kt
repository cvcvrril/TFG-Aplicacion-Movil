package com.example.aplicacionmovilinesmr.domain.modelo.dto

import com.example.aplicacionmovilinesmr.domain.modelo.Ubi
import org.osmdroid.util.GeoPoint

data class UbiDTO (
    val id: Int,
    val lon: Double = 0.0,
    val lat: Double = 0.0,
    val nombre: String? = ""
){



    companion object {
        fun fromUbi(ubi: Ubi): UbiDTO {
            return UbiDTO(
                id = ubi.id,
                lon = ubi.lon,
                lat = ubi.lat,
                nombre = ubi.nombre
            )
        }
    }

}
