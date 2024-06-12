package com.example.aprobarines.data.modelo.response

import android.net.Uri
import com.example.aplicacionmovilinesmr.domain.modelo.Profile


data class AuthorizacionResponse (
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val idUser: Int = 0,
    val username: String,
    val email: String,
    val rol: String,
)

fun AuthorizacionResponse.toProfile() : Profile = Profile(idUser,username,username,email,"", "")