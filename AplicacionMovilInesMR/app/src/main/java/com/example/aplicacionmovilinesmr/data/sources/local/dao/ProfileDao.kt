package com.example.aplicacionmovilinesmr.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionmovilinesmr.data.modelo.entities.ProfileEntity

@Dao
interface ProfileDao {

    @Query("select * from profiles where id = :id limit 1")
    fun getUserProfile(id: Int) : List<ProfileEntity>

    @Insert
    fun addUserProfile(vararg profile: ProfileEntity)

}