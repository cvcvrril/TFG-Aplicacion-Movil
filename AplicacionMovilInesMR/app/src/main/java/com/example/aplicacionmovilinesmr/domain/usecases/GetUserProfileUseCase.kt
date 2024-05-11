package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.repositories.ProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    var repository: ProfileRepository
) {

    suspend operator fun invoke(id: Int) =
        repository.getUserProfile(id)
}