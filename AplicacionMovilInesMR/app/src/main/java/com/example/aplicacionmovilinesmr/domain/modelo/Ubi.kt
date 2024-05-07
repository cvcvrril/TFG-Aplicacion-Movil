package com.example.aplicacionmovilinesmr.domain.modelo

import com.example.aplicacionmovilinesmr.domain.modelo.dto.UbiDTO

data class Ubi(
    val id: Int = 0,
    val long: Double = 0.0,
    val lat: Double = 0.0,
    val idUser: Int = 0,
){
    fun toUbiDTO(): UbiDTO = UbiDTO(id, long, lat)

}

