package com.example.aplicacionmovilinesmr.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aplicacionmovilinesmr.data.modelo.entities.ProfileEntity
import com.example.aplicacionmovilinesmr.data.sources.local.dao.ProfileDao

@Database(entities = [ProfileEntity::class],version = 1, exportSchema = true)
abstract class ProfileDatabase : RoomDatabase()  {
    abstract fun profileDao() : ProfileDao

}