package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.repositories.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    var repository: ProfileRepository
) {

    suspend operator fun invoke(id: Int) = withContext(Dispatchers.IO) {
        repository.getUserProfile(id)
    }
}