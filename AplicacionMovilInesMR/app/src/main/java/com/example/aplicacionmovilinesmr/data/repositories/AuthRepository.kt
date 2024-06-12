package com.example.aplicacionmovilinesmr.data.repositories

import com.example.aplicacionmovilinesmr.data.sources.remote.AuthRemoteDataSource
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.TokenManager
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.UserManager
import com.example.aplicacionmovilinesmr.domain.modelo.Credential
import com.example.aplicacionmovilinesmr.utils.NetworkResult
import com.example.aprobarines.data.modelo.response.AuthorizacionResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    fun login(username: String, password: String) : Flow<NetworkResult<AuthorizacionResponse>> {
        return flow {
            emit(NetworkResult.Loading())
            val res = remoteDataSource.login(username, password)
            emit(res)
        }.flowOn(dispatcher)

    }

    fun registro(credential: Credential) : Flow<NetworkResult<Unit>>{
        return flow {
            emit(NetworkResult.Loading())
            val res = remoteDataSource.registro(credential)
            emit(res)
        }.flowOn(dispatcher)
    }

    fun darBaja(email: String) : Flow<NetworkResult<Unit>>{
        return flow {
            emit(NetworkResult.Loading())
            val res = remoteDataSource.darBaja(email)
            emit(res)
        }.flowOn(dispatcher)
    }

    fun forgotPassword(email: String) : Flow<NetworkResult<Unit>>{
        return flow {
            emit(NetworkResult.Loading())
            val res = remoteDataSource.forgotPassword(email)
            emit(res)
        }.flowOn(dispatcher)
    }

}