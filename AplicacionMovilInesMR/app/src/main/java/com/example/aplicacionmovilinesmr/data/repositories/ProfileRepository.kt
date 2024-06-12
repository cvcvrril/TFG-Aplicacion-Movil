package com.example.aplicacionmovilinesmr.data.repositories

import com.example.aplicacionmovilinesmr.data.modelo.entities.ProfileEntity
import com.example.aplicacionmovilinesmr.data.modelo.entities.toProfile
import com.example.aplicacionmovilinesmr.data.sources.local.dao.ProfileDao
import com.example.aplicacionmovilinesmr.domain.modelo.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {

    suspend fun addUserProfile(profile: ProfileEntity) =
        withContext(Dispatchers.IO) {
            profileDao.addUserProfile(profile)
        }

    suspend fun getUserProfile(id: Int) =
        withContext(Dispatchers.IO) {
            profileDao.getUserProfile(id).map { it.toProfile() }
        }

    suspend fun getUserProfileByUsername(username: String) =
        withContext(Dispatchers.IO) {
            profileDao.getUserProfileByUsername(username).map { it.toProfile() }
        }

    suspend fun updateUserProfile(profile: ProfileEntity) =
        withContext(Dispatchers.IO) {
            profileDao.updateUserProfile(profile)
        }

    suspend fun deleteUserProfile(profile: ProfileEntity) =
        withContext(Dispatchers.IO) {
            profileDao.deleteUserProfile(profile)
        }
}