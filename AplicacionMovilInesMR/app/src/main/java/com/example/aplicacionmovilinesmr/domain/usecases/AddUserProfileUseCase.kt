package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.repositories.ProfileRepository
import com.example.aplicacionmovilinesmr.domain.modelo.Profile
import com.example.aplicacionmovilinesmr.domain.modelo.toProfileEntity
import javax.inject.Inject

class AddUserProfileUseCase @Inject constructor(
    var repository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) =
        repository.addUserProfile(profile.toProfileEntity())

}