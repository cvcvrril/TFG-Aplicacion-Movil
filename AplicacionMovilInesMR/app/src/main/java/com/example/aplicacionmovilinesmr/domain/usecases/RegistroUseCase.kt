package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.repositories.AuthRepository
import com.example.aplicacionmovilinesmr.domain.modelo.Credential
import javax.inject.Inject

class RegistroUseCase @Inject constructor(
    var repository: AuthRepository,
) {
    suspend operator fun invoke(credential: Credential) =
        repository.registro(credential)

}