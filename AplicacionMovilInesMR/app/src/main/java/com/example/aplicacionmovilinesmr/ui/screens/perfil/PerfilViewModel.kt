package com.example.aplicacionmovilinesmr.ui.screens.perfil

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.UserManager
import com.example.aplicacionmovilinesmr.domain.usecases.GetUserProfileUseCase
import com.example.aplicacionmovilinesmr.domain.usecases.UpdateUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val userManager: UserManager,
) : ViewModel() {

    private val _uiState: MutableStateFlow<PerfilState> by lazy {
        MutableStateFlow(PerfilState())
    }
    val uiState: StateFlow<PerfilState> = _uiState

    init {
        _uiState.value = PerfilState(
            error = null,
            loading = false
        )
        getPerfil()
    }

    fun handleEvent(event: PerfilEvent, context: Context? = null) {
        when (event) {
            PerfilEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
            is PerfilEvent.UpdatePerfil -> updatePerfil()
            is PerfilEvent.OnEditModeChange -> {
                val newMode = !event.editmode
                _uiState.update { it.copy(editmode = newMode) }
            }

            is PerfilEvent.OnDescripciionChange -> _uiState.update {
                it.copy(
                    perfil = it.perfil?.copy(
                        descripcion = event.descripcion
                    )
                )
            }

            is PerfilEvent.OnNombreCompletoChange -> _uiState.update {
                it.copy(
                    perfil = it.perfil?.copy(
                        nombreCompleto = event.nombreCompleto
                    )
                )
            }

            is PerfilEvent.OnImagenChange -> {
                _uiState.update {
                    it.copy(
                        perfil = it.perfil?.copy(
                            image = event.imageUri.toString()
                        )
                    )
                }
                context?.let { ctx ->
                    viewModelScope.launch {
                        saveImageToInternalStorage(ctx, event.imageUri, _uiState.value.nombreCompleto ?: "default_username" )
                    }
                }
            }
        }
    }


    private fun saveImageToInternalStorage(context: Context, uri: Uri, username: String) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                Log.e("saveImageToInternalStorage", "InputStream is null for URI: $uri")
                return
            }

            val outputStream = context.openFileOutput("$username.jpg", Context.MODE_PRIVATE)
            Log.d("ImagenLocal", "Nombre del archivo : $username.jpg")

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                    Log.d("saveImageToInternalStorage", "Image saved successfully as $username.jpg")
                }
            }
        } catch (e: Exception) {
            Log.e("saveImageToInternalStorage", "Error saving image: ${e.message}", e)
        }
    }


    private fun getPerfil() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            val idUser = runBlocking { userManager.getIdUser().first()?.toInt() }
            if (idUser != null) {
                val userdb = getUserProfileUseCase.invoke(idUser)
                if (userdb.isEmpty()) {
                    _uiState.update { it.copy(error = "No hay usuario") }
                } else {
                    _uiState.update { it.copy(perfil = userdb.first()) }
                }
            }
            _uiState.update { it.copy(loading = false) }
        }
    }

    private fun updatePerfil() {
        viewModelScope.launch {
            _uiState.value.perfil?.let {
                updateUserProfileUseCase.invoke(it)
            }
        }
    }

}