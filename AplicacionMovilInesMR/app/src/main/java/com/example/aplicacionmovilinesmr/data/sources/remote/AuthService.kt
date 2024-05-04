package com.example.aplicacionmovilinesmr.data.sources.remote

import com.example.aplicacionmovilinesmr.data.modelo.request.CredentialRequest
import com.example.aprobarines.data.modelo.response.AuthorizacionResponse
import com.example.aprobarines.data.modelo.response.CredentialResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @GET("login")
    suspend fun login(@Query("username") username : String, @Query("password") password: String ): Response<AuthorizacionResponse>

    @POST("registro")
    suspend fun registro(@Body credentialRequest: CredentialRequest) : Response<Unit>

    @POST("forgotPassword")
    suspend fun forgotPassword(@Query("email") email: String): Response<Unit>

}