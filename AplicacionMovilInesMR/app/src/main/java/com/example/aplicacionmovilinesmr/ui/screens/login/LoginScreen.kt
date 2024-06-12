package com.example.aplicacionmovilinesmr.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aplicacionmovilinesmr.R
import kotlin.reflect.KFunction1

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginDone: () -> Unit,
    toForgotPassword: () -> Unit,
    toRegistro: () -> Unit,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    OnLogin(
        state = state.value,
        handleEvent = viewModel::handleEvent,
        onLoginDone = onLoginDone,
        toForgotPassword = toForgotPassword,
        toRegistro = toRegistro,
    )
}

@Composable
fun OnLogin(
    state: LoginState,
    handleEvent: KFunction1<LoginEvent, Unit>,
    onLoginDone: () -> Unit,
    toForgotPassword: () -> Unit,
    toRegistro: () -> Unit,
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LoginTitle()
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_40)))
            UsernameTextField(
                state = state,
                handleEvent = handleEvent,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            PasswordTextField(
                state = state,
                handleEvent = handleEvent,
            )
            Row (
                horizontalArrangement = Arrangement.End
            ){
                TextButton(
                    onClick = {
                        toForgotPassword()
                    }) {
                    Text(text = "He olvidado la contraseña")
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            Button(
                onClick = {
                    handleEvent(LoginEvent.Login())
                }) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            Text("¿No tienes una cuenta?")
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            OutlinedButton(
                onClick = {
                    toRegistro()
                }) {
                Text(text = "Ir al Registro")
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))


        }


    }
}

@Composable
fun LoginTitle(){
    Text(
        text = "¡Bienvenido de vuelta!",
        fontSize = 26.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun UsernameTextField(
    state: LoginState,
    handleEvent: KFunction1<LoginEvent, Unit>,
) {
    OutlinedTextField(
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = "Account username"
            )
        },
        placeholder = { Text(text = "Username") },
        value = state.username ?: "",
        onValueChange = { valueIntroduced ->
            handleEvent(LoginEvent.OnUserNameChange(valueIntroduced))
        })
}

@Composable
fun PasswordTextField(
    state: LoginState,
    handleEvent: KFunction1<LoginEvent, Unit>,
) {

    var showPassword by remember { mutableStateOf(value = false) }

    OutlinedTextField(
        leadingIcon = {
            Icon(imageVector = Icons.Default.Password,
                contentDescription = "Password Account"
            )
        },
        placeholder = { Text(text = "Password") },
        value = state.password ?: "",
        onValueChange = { valueIntroduced ->
            handleEvent(LoginEvent.OnPasswordChange(valueIntroduced))
        },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "hide_password"
                    )
                }
            } else {
                IconButton(
                    onClick = { showPassword = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "hide_password"
                    )
                }
            }
        }
    )

}
