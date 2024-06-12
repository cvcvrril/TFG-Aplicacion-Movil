package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.repositories.ProfileRepository
import com.example.aplicacionmovilinesmr.domain.modelo.Profile
import com.example.aplicacionmovilinesmr.domain.modelo.toProfileEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteUserProfileUseCase @Inject constructor(
    var repository: ProfileRepository,
){

    suspend operator fun invoke(perfil: Profile) = withContext(Dispatchers.IO) {
        repository.deleteUserProfile(perfil.toProfileEntity())
    }

}