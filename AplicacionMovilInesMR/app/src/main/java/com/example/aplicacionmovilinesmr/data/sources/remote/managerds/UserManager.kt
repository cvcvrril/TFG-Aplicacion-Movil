package com.example.aplicacionmovilinesmr.data.sources.remote.managerds

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.aplicacionmovilinesmr.data.sources.remote.di.NetworkModule.dataStore

import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserManager @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val idUser = stringPreferencesKey("id_user")
    }

    fun getIdUser(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[idUser]
        }
    }

    suspend fun saveIdUser(id: String) {
        context.dataStore.edit { preferences ->
            preferences[idUser] = id
        }
    }
}