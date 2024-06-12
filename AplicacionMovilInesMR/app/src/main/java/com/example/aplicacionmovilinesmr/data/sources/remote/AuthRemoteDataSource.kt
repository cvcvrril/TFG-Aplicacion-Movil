package com.example.aplicacionmovilinesmr.data.sources.remote

import com.example.aplicacionmovilinesmr.data.repositories.ProfileRepository
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.TokenManager
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.UserManager
import com.example.aplicacionmovilinesmr.domain.modelo.Credential
import com.example.aplicacionmovilinesmr.domain.modelo.toProfileEntity
import com.example.aplicacionmovilinesmr.utils.NetworkResult
import com.example.aprobarines.data.modelo.response.AuthorizacionResponse
import com.example.aprobarines.data.modelo.response.toProfile
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val service: AuthService,
    private val tokenManager: TokenManager,
    private val userManager: UserManager,
    private val repository: ProfileRepository,
) {

    suspend fun login(username : String, password : String) : NetworkResult<AuthorizacionResponse>{
        try {
            val response = service.login(username, password)
            if (response.isSuccessful){
                val body = response.body()
                body?.let {
                    val accessToken = body.accessToken
                    val refreshToken = body.refreshToken
                    val idUser = body.idUser
                    if (accessToken != null) {
                        tokenManager.saveAccessToken(accessToken)
                        refreshToken?.let { it1 -> tokenManager.saveRefreshToken(it1) }
                        userManager.saveIdUser(idUser.toString())
                        val userProfile = body.toProfile()
                        if (repository.getUserProfileByUsername(userProfile.username).isEmpty()) {
                            repository.addUserProfile(userProfile.toProfileEntity())
                        }
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

    suspend fun registro(newCredential: Credential) : NetworkResult<Unit>{
        return try {
            val response = service.registro(newCredential.toCredentialRequest())
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun forgotPassword(email : String) : NetworkResult<Unit>{
        return try {
            val response = service.forgotPassword(email)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error("${response.code()} ${response.message()}")
            }
        }catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun darBaja(email: String) : NetworkResult<Unit>{
        return try {
            val response = service.darBaja(email)
            if (response.isSuccessful){
                NetworkResult.Success(Unit)
            }else{
                NetworkResult.Error("${response.code()} ${response.message()}")
            }
        }catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

}