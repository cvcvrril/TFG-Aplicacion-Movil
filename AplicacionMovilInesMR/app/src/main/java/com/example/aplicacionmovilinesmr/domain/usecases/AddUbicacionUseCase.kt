package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.repositories.UbiRepository
import com.example.aplicacionmovilinesmr.domain.modelo.Ubi
import javax.inject.Inject

class AddUbicacionUseCase @Inject constructor(
    val repository: UbiRepository
) {

    suspend operator fun invoke(newUbi : Ubi) =
        repository.addUbicacion(newUbi)
}