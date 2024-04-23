package com.example.aplicacionmovilinesmr.data.sources.remote

import com.example.aplicacionmovilinesmr.utils.NetworkResult
import com.example.aprobarines.data.modelo.response.AuthorizacionResponse
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val service: AuthService,
) {

    suspend fun login(username : String, password : String) : NetworkResult<AuthorizacionResponse>{
        try {
            val response = service.login(username, password)
            if (response.isSuccessful){
                val body = response.body()
                body?.let {
                    val accessToken = body.accessToken
                    val refreshToken = body.refreshToken
                    if (accessToken != null) {
                        return NetworkResult.Success(body)
                    }else{
                        return NetworkResult.Error("El Access Token recibido es nulo.")
                    }
                }
            }else {
                if (response.code() == 403){
                    return NetworkResult.Error("El usuario o la contraseña introducidos son nulos.")
                }else{
                    return NetworkResult.Error("${response.code()} ${response.message()}")
                }
            }
        }catch (e: Exception){
            return NetworkResult.Error(e.message ?: e.toString())
        }
        return NetworkResult.Error("Hubo un error al hacer login.")
    }

}