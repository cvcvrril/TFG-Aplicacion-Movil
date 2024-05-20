package com.example.aplicacionmovilinesmr.domain.modelo

import com.example.aplicacionmovilinesmr.data.modelo.request.NewUbiDTO
import com.example.aplicacionmovilinesmr.domain.modelo.dto.UbiDTO

data class Ubi(
    val id: Int = 0,
    val long: Double,
    val lat: Double,
    var idUser: Int = 0,
){
    fun toUbiDTO(): UbiDTO = UbiDTO(id, long, lat)
    fun toNewUbiDTO() : NewUbiDTO = NewUbiDTO(long, lat, idUser)

}

