package com.example.aplicacionmovilinesmr.data.modelo.entities

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.aplicacionmovilinesmr.domain.modelo.Profile

@Entity(tableName = "profiles")
data class ProfileEntity(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "nombre_completo")
    val nombreCompleto: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "descripcion")
    val descripcion: String,
    @ColumnInfo(name = "image")
    val image: String,
)

fun ProfileEntity.toProfile() : Profile = Profile(id, username, nombreCompleto, email, descripcion, image)