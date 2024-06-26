package com.example.aplicacionmovilinesmr.data.repositories

import com.example.aplicacionmovilinesmr.data.sources.remote.UbiRemoteDataSource
import com.example.aplicacionmovilinesmr.domain.modelo.Ubi
import com.example.aplicacionmovilinesmr.domain.modelo.dto.UbiDTO
import com.example.aplicacionmovilinesmr.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class UbiRepository @Inject constructor(
    private val remoteDataSource: UbiRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
){

    fun getUbicaciones(id: Int) : Flow<NetworkResult<List<UbiDTO>>> {
        return flow {
            emit(NetworkResult.Loading())
            val res = remoteDataSource.getUbicaciones(id)
            emit(res)
        }.flowOn(dispatcher)
    }

    fun deleteUbicacion(id: Int) : Flow<NetworkResult<Unit>>{
        return flow {
            emit(NetworkResult.Loading())
            val res = remoteDataSource.deleteUbicacion(id)
            emit(res)
        }.flowOn(dispatcher)
    }

    fun addUbicacion(newUbi : Ubi) : Flow<NetworkResult<Unit>>{
        return flow {
            emit(NetworkResult.Loading())
            val res = remoteDataSource.addUbicacion(newUbi)
            emit(res)
        }.flowOn(dispatcher)
    }

}