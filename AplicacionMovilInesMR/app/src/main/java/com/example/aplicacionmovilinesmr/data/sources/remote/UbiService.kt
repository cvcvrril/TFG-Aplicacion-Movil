package com.example.aplicacionmovilinesmr.data.sources.remote

import com.example.aplicacionmovilinesmr.data.modelo.request.NewUbiDTO
import com.example.aplicacionmovilinesmr.domain.modelo.Ubi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UbiService {

    @GET("ubiuser")
    suspend fun getUbicaciones(@Query("id_user") id: Int) : Response<List<Ubi>>
    @DELETE("delete")
    suspend fun deleteUbicacion(@Query("id") id: Int) : Response<Unit>
    @POST("add")
    suspend fun addUbicacion(@Body newUbi: NewUbiDTO) : Response<Unit>

}