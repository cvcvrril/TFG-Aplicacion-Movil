package com.example.aplicacionmovilinesmr.domain.usecases

import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.UserManager
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    var userManager: UserManager,
){
    suspend fun logoutUser() = userManager.deleteEverything()

}