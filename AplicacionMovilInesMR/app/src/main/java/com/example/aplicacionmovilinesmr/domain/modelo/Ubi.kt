package com.example.aplicacionmovilinesmr.domain.modelo

import com.example.aplicacionmovilinesmr.data.modelo.request.NewUbiDTO
import com.example.aplicacionmovilinesmr.domain.modelo.dto.UbiDTO

data class Ubi(
    val id: Int = 0,
    val lat: Double,
    val lon: Double,
    var idUser: Int = 0,
    val nombre: String? = ""
){
    fun toUbiDTO(): UbiDTO = UbiDTO(id, lat, lon)
    fun toNewUbiDTO() : NewUbiDTO = NewUbiDTO(lat, lon, idUser)

}

