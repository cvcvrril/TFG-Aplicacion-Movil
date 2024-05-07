package com.example.aplicacionmovilinesmr.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlin.reflect.KFunction1

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginDone: () -> Unit,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    OnLogin(
        state = state.value,
        handleEvent = viewModel::handleEvent,
        navController = navController,
        onLoginDone = onLoginDone,
    )
}

@Composable
fun OnLogin(
    navController: NavController,
    state: LoginState,
    handleEvent: KFunction1<LoginEvent, Unit>,
    onLoginDone: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.login) {
        if (state.login) {
            onLoginDone()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) })
    { innerPadding ->
        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    duration = SnackbarDuration.Short
                )
            }
        }
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Login")
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                placeholder = { Text(text = "Username") },
                value = state.username ?: "",
                onValueChange = { valueIntroduced ->
                    handleEvent(LoginEvent.OnUserNameChange(valueIntroduced))
                })
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                placeholder = { Text(text = "Password") },
                value = state.password ?: "",
                onValueChange = { valueIntroduced ->
                    handleEvent(LoginEvent.OnPasswordChange(valueIntroduced))
                },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    handleEvent(LoginEvent.Login())
                }) {
                Text(text = "Login")
            }
            Text("¿No tienes una cuenta?")
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    navController.navigate("registro")
                }) {
                Text(text = "Ir al Registro")
            }

        }


    }
}
