package com.example.aplicacionmovilinesmr.data.sources.remote

import com.example.aplicacionmovilinesmr.domain.modelo.Ubi
import com.example.aplicacionmovilinesmr.domain.modelo.dto.UbiDTO
import com.example.aplicacionmovilinesmr.utils.NetworkResult
import javax.inject.Inject

class UbiRemoteDataSource @Inject constructor(
    private val service: UbiService,
) {

    suspend fun getUbicaciones(id: Int) : NetworkResult<List<UbiDTO>>{
        try {
            val response = service.getUbicaciones(id)
            if (response.isSuccessful){
                val body = response.body()
                body?.let {
                    val ubicacionesDTO = body.map { UbiDTO.fromUbi(it) }
                    return NetworkResult.Success(ubicacionesDTO)
                }
            }
        }catch (e: Exception){
            return NetworkResult.Error(e.message ?: e.toString())
        }
        return NetworkResult.Error("Hubo un error al tratar de conseguir la lista de ubicaciones.")
    }

}