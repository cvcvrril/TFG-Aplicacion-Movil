package com.example.aplicacionmovilinesmr.ui.screens.mapa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.UserManager
import com.example.aplicacionmovilinesmr.domain.modelo.Ubi
import com.example.aplicacionmovilinesmr.domain.usecases.AddUbicacionUseCase
import com.example.aplicacionmovilinesmr.domain.usecases.GetUbicacionesUseCase
import com.example.aplicacionmovilinesmr.ui.screens.ubicaciones.UbicacionesEvent
import com.example.aplicacionmovilinesmr.ui.screens.ubicaciones.UbicacionesState
import com.example.aplicacionmovilinesmr.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MapaViewModel @Inject constructor(
    private val addUbicacionUseCase: AddUbicacionUseCase,
    private val getUbicacionesUseCase: GetUbicacionesUseCase,
    private val userManager: UserManager,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MapaState> by lazy {
        MutableStateFlow(MapaState())
    }
    val uiState: StateFlow<MapaState> = _uiState


    init {
        _uiState.value = MapaState(
            ubicaciones = emptyList(),
            error = null,
            loading = false
        )
        getUbicaciones()
    }

    fun handleEvent(event: MapaEvent) {
        when (event) {
            is MapaEvent.AddUbicacion -> {
                addUbicacion(event.newUbi)
            }

            MapaEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
            is MapaEvent.GetUbicaciones -> getUbicaciones()
        }
    }

    private fun addUbicacion(newUbi: Ubi) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            val idUser = runBlocking { userManager.getIdUser().first()?.toInt() }
            if (idUser != null) {
                newUbi.idUser = idUser
                addUbicacionUseCase.invoke(newUbi)
                    .collect { res ->
                        when (res) {
                            is NetworkResult.Error -> _uiState.update {
                                it.copy(
                                    error = res.message,
                                    loading = false,
                                )
                            }

                            is NetworkResult.Loading -> _uiState.update {
                                it.copy(loading = false)
                            }

                            is NetworkResult.Success -> {
                                res.data?.let { ubicaciones ->
                                    _uiState.update {
                                        it.copy(
                                            error = "Ubicación agregada con éxito.",
                                            loading = true,
                                        )
                                    }
                                    getUbicaciones()
                                }

                            }
                        }
                    }
            }
        }
    }

    private fun getUbicaciones() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            val idUser = runBlocking { userManager.getIdUser().first()?.toInt() }
            if (idUser != null) {
                getUbicacionesUseCase.invoke(idUser)
                    .collect { res ->
                        when (res) {
                            is NetworkResult.Error -> _uiState.update {
                                it.copy(
                                    error = res.message,
                                    loading = false,
                                )
                            }

                            is NetworkResult.Loading -> _uiState.update {
                                it.copy(loading = false)
                            }

                            is NetworkResult.Success -> {
                                res.data?.let { ubicaciones ->
                                    _uiState.update {
                                        it.copy(
                                            ubicaciones = ubicaciones,
                                            loading = true,
                                        )
                                    }

                                }

                            }
                        }
                    }
            }
        }
    }

}