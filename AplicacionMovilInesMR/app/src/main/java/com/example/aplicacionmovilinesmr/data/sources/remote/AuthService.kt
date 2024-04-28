package com.example.aplicacionmovilinesmr.data.sources.remote

import com.example.aprobarines.data.modelo.response.AuthorizacionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @GET("/auth/login")
    suspend fun login(@Query("username") username : String, @Query("password") password: String ): Response<AuthorizacionResponse>
}