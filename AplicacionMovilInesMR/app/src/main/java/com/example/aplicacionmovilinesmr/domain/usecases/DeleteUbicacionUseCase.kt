package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.repositories.UbiRepository
import javax.inject.Inject

class DeleteUbicacionUseCase @Inject constructor(
    var repository: UbiRepository,
){
    suspend fun invoke(id: Int) =
        repository.deleteUbicacion(id)

}