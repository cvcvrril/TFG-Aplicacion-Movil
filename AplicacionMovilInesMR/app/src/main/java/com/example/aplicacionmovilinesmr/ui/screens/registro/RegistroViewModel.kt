package com.example.aplicacionmovilinesmr.ui.screens.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionmovilinesmr.domain.usecases.RegistroUseCase
import com.example.aplicacionmovilinesmr.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val registroUseCase: RegistroUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<RegistroState> by lazy {
        MutableStateFlow(RegistroState())
    }
    val uiState: StateFlow<RegistroState> = _uiState

    fun handleEvent(event: RegistroEvent) {
        when (event) {
            is RegistroEvent.Registro -> {
                registro()
            }

            RegistroEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }

            is RegistroEvent.OnPasswordChange -> _uiState.update {
                it.copy(
                    newCredential = it.newCredential.copy(
                        password = event.password
                    ),
                )
            }

            is RegistroEvent.OnUserNameChange -> _uiState.update {
                it.copy(
                    newCredential = it.newCredential.copy(
                        username = event.username
                    ),
                )
            }

            is RegistroEvent.OnEmailChange -> _uiState.update {
                it.copy(
                    newCredential = it.newCredential.copy(
                        email = event.email,
                    )

                )
            }

            is RegistroEvent.OnNombreCompletoChange -> _uiState.update {
                it.copy(
                    newCredential = it.newCredential.copy(
                        nombreCompleto = event.nombreCompleto,
                    ),

                    )
            }

            is RegistroEvent.OnFechaNacimientoChange -> _uiState.update {
                it.copy(
                    newCredential = it.newCredential.copy(
                        fechaNacimiento = event.fechaNacimiento,
                    ),

                    )
            }
        }
    }

    private fun registro() {
        if (_uiState.value.newCredential.username.isEmpty() || _uiState.value.newCredential.password.isEmpty() || _uiState.value.newCredential.username.isBlank() || _uiState.value.newCredential.password.isBlank()) {
            _uiState.update { it.copy(error = "Hay campos vacíos") }
        } else {
            viewModelScope.launch {
                registroUseCase.invoke(_uiState.value.newCredential!!)
                    .catch(action = { cause ->
                        _uiState.update {
                            it.copy(
                                error = cause.message,
                                loading = false
                            )
                        }
                    })
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        error = result.message,
                                        loading = false
                                    )
                                }
                            }

                            is NetworkResult.Loading -> _uiState.update { it.copy(loading = true) }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    error = "Registro realizado correctamente",
                                    loading = false
                                )
                            }
                        }
                    }
            }
        }
    }

}