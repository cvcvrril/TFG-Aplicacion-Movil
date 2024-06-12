package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.repositories.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    var repository: AuthRepository
){
    suspend operator fun invoke(email: String) =
        repository.forgotPassword(email)

}