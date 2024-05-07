package com.example.aplicacionmovilinesmr.data.sources.remote

import com.example.aplicacionmovilinesmr.domain.modelo.Ubi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UbiService {

    @GET("ubiuser")
    suspend fun getUbicaciones(@Query("id_user") id: Int) : Response<List<Ubi>>

}