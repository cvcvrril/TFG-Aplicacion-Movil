package com.example.aplicacionmovilinesmr.data.repositories

import com.example.aplicacionmovilinesmr.data.modelo.entities.ProfileEntity
import com.example.aplicacionmovilinesmr.data.modelo.entities.toProfile
import com.example.aplicacionmovilinesmr.data.sources.local.dao.ProfileDao
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {

    suspend fun addUserProfile(profile: ProfileEntity) =
        profileDao.addUserProfile(profile)

    suspend fun getUserProfile(id: Int) =
        profileDao.getUserProfile(id).map { it.toProfile() }
}