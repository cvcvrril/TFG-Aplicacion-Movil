package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    var repository: AuthRepository,
){
    suspend operator fun invoke (username : String, password : String) =
        repository.login(username, password)
}