package com.example.aplicacionmovilinesmr.ui.screens.forgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionmovilinesmr.domain.usecases.ForgotPasswordUseCase
import com.example.aplicacionmovilinesmr.ui.screens.login.LoginState
import com.example.aplicacionmovilinesmr.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel(){

    private val _uiState: MutableStateFlow<ForgotPasswordState> by lazy {
        MutableStateFlow(ForgotPasswordState())
    }

    val uiState: StateFlow<ForgotPasswordState> = _uiState

    init {
        _uiState.value = ForgotPasswordState(
            error = null,
        )
    }

    fun handleEvent(event: ForgotPasswordEvent){
        when(event){
            ForgotPasswordEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
            is ForgotPasswordEvent.OnEmailChange -> _uiState.update {
                it.copy(
                    email = event.email
                )
            }

            is ForgotPasswordEvent.ForgotPassword -> forgotPassword()
        }
    }

    private fun forgotPassword(){
        viewModelScope.launch {
            val email = _uiState.value.email
            if (email!= null){
                forgotPasswordUseCase.invoke(email)
                    .collect{res ->
                        when(res){
                            is NetworkResult.Error -> _uiState.update {
                                it.copy(
                                    error = res.message,
                                    loading = false,
                                )
                            }
                            is NetworkResult.Loading -> _uiState.update {
                                it.copy(loading = false)
                            }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    error = "Por favor revise la bandeja de entrada de su correo.",
                                    loading = false
                                )
                            }
                        }

                    }
            }

        }
    }

}