package com.example.aplicacionmovilinesmr.ui.screens.ubicaciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.UserManager
import com.example.aplicacionmovilinesmr.domain.usecases.GetUbicacionesUseCase
import com.example.aplicacionmovilinesmr.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class UbicacionesViewModel @Inject constructor(
    private val getUbicacionesUseCase: GetUbicacionesUseCase,
    private val userManager: UserManager,
) : ViewModel(){

    private val _uiState: MutableStateFlow<UbicacionesState> by lazy {
        MutableStateFlow(UbicacionesState())
    }
    val uiState: StateFlow<UbicacionesState> = _uiState

    init {
        _uiState.value = UbicacionesState(
            ubicaciones = emptyList(),
            error = null,
            loading = false
        )
        getUbicaciones()
    }

    fun handleEvent(event : UbicacionesEvent){
        when(event){
            UbicacionesEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
            is UbicacionesEvent.GetUbicaciones -> {
                getUbicaciones()
            }
        }
    }

    private fun getUbicaciones() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            val idUser = runBlocking { userManager.getIdUser().first()?.toInt() }
            if (idUser != null) {
                getUbicacionesUseCase.invoke(idUser)
                    .collect{res ->
                        when (res){
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
                                res.data?.let {ubicaciones ->
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