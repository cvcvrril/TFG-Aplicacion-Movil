package com.example.aplicacionmovilinesmr.ui.screens.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicacionmovilinesmr.R
import kotlin.reflect.KFunction1

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    toLogin: () -> Unit,
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    GetForgotPasswordScreen(
        state = state.value,
        handleEvent = viewModel::handleEvent,
        forgotPassword = { viewModel.handleEvent(ForgotPasswordEvent.ForgotPassword()) },
        toLogin = toLogin,
    )

}

@Composable
fun GetForgotPasswordScreen(
    state: ForgotPasswordState,
    handleEvent: KFunction1<ForgotPasswordEvent, Unit>,
    forgotPassword: () -> Unit,
    toLogin: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(
                    message = state.error.toString(),
                    duration = SnackbarDuration.Short,
                )
            }
        }
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Nueva contraseña")
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            OutlinedTextField(
                leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Account Email"
                )
            },
                placeholder = { Text(text = "Email") },
                value = state.email,
                onValueChange = { valueIntroduced ->
                    handleEvent(ForgotPasswordEvent.OnEmailChange(valueIntroduced))
                })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            Button(
                onClick = {
                    forgotPassword()
                }) {
                Text(text = "Recuperar contraseña")
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            OutlinedButton(
                onClick = {
                    toLogin()
                }) {
                Text(text = "Ir al Login")
            }
        }
    }

}