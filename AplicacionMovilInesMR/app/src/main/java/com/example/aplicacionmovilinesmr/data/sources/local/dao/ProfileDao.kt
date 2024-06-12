package com.example.aplicacionmovilinesmr.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.aplicacionmovilinesmr.data.modelo.entities.ProfileEntity

@Dao
interface ProfileDao {

    @Query("select * from profiles where id = :id limit 1")
    fun getUserProfile(id: Int) : List<ProfileEntity>

    @Query("select * from profiles where username = :username limit 1")
    fun getUserProfileByUsername(username: String) : List<ProfileEntity>

    @Insert
    fun addUserProfile(vararg profile: ProfileEntity)

    @Update
    fun updateUserProfile(vararg profile: ProfileEntity)

    @Delete
    fun deleteUserProfile(vararg profile: ProfileEntity)

}