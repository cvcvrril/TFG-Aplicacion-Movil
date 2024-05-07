package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.repositories.UbiRepository
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.UserManager
import javax.inject.Inject

class GetUbicacionesUseCase @Inject constructor(
    var repository: UbiRepository,
){
    suspend operator fun invoke(id: Int) =
        repository.getUbicaciones(id)
}