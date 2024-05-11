package com.example.aplicacionmovilinesmr.data.sources.local.di

import android.content.Context
import androidx.room.Room
import com.example.aplicacionmovilinesmr.data.sources.local.ProfileDatabase
import com.example.aplicacionmovilinesmr.data.sources.local.dao.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ProfileDatabase {
        return Room.databaseBuilder(
            appContext,
            ProfileDatabase::class.java,
            "app.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideProfileDao(appDatabase: ProfileDatabase) : ProfileDao{
        return appDatabase.profileDao()
    }

}