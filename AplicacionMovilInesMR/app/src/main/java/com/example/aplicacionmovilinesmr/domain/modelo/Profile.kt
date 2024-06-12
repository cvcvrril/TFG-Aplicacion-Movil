package com.example.aplicacionmovilinesmr.domain.modelo

import android.net.Uri
import com.example.aplicacionmovilinesmr.data.modelo.entities.ProfileEntity

data class Profile(
    val id: Int,
    val username: String,
    val nombreCompleto: String,
    val email: String,
    val descripcion: String,
    val image: String,
)

fun Profile.toProfileEntity() : ProfileEntity = ProfileEntity(id, username, nombreCompleto, email, descripcion, image.toString())