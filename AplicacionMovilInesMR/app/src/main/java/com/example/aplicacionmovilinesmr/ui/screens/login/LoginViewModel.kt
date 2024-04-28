package com.example.aplicacionmovilinesmr.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionmovilinesmr.domain.usecases.LoginUseCase
import com.example.aplicacionmovilinesmr.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginState> by lazy {
        MutableStateFlow(LoginState())
    }
    val uiState: StateFlow<LoginState> = _uiState

    init {
        _uiState.value = LoginState(
            error = null,
            username = null,
            password = null,
        )
    }

    fun handleEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.ErrorVisto -> _uiState.update { it.copy(error = null) }
            is LoginEvent.Login -> {
                login()
            }

            is LoginEvent.OnPasswordChange -> _uiState.update {
                it.copy(
                    password = event.password,
                )
            }

            is LoginEvent.OnUserNameChange -> _uiState.update {
                it.copy(
                    username = event.username,
                )
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            loginUseCase.invoke(_uiState.value.username!!, _uiState.value.password!!)
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

                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                login = true,
                                loading = false
                            )
                        }
                    }
                }
        }
    }


}