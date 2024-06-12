package com.example.aplicacionmovilinesmr.ui.screens.registro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicacionmovilinesmr.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.reflect.KFunction1

@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel = hiltViewModel(),
    toLogin: () -> Unit,
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    OnRegistro(
        state = state.value,
        handleEvent = viewModel::handleEvent,
        registro = { viewModel.handleEvent(RegistroEvent.Registro()) },
        onUserNameChange = { viewModel.handleEvent(RegistroEvent.OnUserNameChange(it)) },
        onPasswordChange = { viewModel.handleEvent(RegistroEvent.OnPasswordChange(it)) },
        toLogin = toLogin,
    )

}

@Composable
fun OnRegistro(
    state: RegistroState,
    registro: () -> Unit,
    handleEvent: KFunction1<RegistroEvent, Unit>,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RegistroTitle()
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            UsernameTextField(
                state = state,
                onUserNameChange = onUserNameChange,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            PasswordTextField(
                state = state,
                onPasswordChange = onPasswordChange,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            OutlinedTextField(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Account Nombre Completo"
                    )
                },
                placeholder = { Text(text = "Nombre Completo") },
                value = state.newCredential.nombreCompleto,
                onValueChange = { valueIntroduced ->
                    handleEvent(RegistroEvent.OnNombreCompletoChange(valueIntroduced))
                })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            OutlinedTextField(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Account Email"
                    )
                },
                placeholder = { Text(text = "Email") },
                value = state.newCredential.email,
                onValueChange = { valueIntroduced ->
                    handleEvent(RegistroEvent.OnEmailChange(valueIntroduced))
                })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            CustomDatePicker(
                state = state,
                handleEvent = handleEvent
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            Button(
                onClick = registro
            ) {
                Text(text = "Registro")
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.normal_spacer_20)))
            Text("¿Tienes ya una cuenta?")
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

@Composable
fun RegistroTitle(){
    Text(
        text = "Crea tu cuenta",
        fontSize = 26.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun CustomDatePicker(
    state: RegistroState,
    handleEvent: KFunction1<RegistroEvent, Unit>,
) {

    val isOpen = remember {
        mutableStateOf(false)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            readOnly = true,
            value = state.newCredential.fechaNacimiento.format(DateTimeFormatter.ISO_DATE),
            placeholder = { Text(text = "Fecha de nacimiento") },
            onValueChange = { valueIntroduced ->
                handleEvent(RegistroEvent.OnFechaNacimientoChange(valueIntroduced))
            }
        )
        IconButton(onClick = { isOpen.value = true }) {
            Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Calendar")
        }
    }

    if (isOpen.value) {
        CustomDatePickerDialog(
            onAccept = {
                isOpen.value = false

                if (it != null) {
                    state.newCredential.fechaNacimiento =
                        Instant
                            .ofEpochMilli(it)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()
                            .toString()
                }
            }, onCancel = {
                isOpen.value = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit,
) {
    val state = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = { onAccept(state.selectedDateMillis) }) {
                Text(text = "Accept")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = state)
    }
}

@Composable
fun PasswordTextField(
    state: RegistroState,
    onPasswordChange: (String) -> Unit,
) {

    var showPassword by remember { mutableStateOf(value = false) }

    OutlinedTextField(
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Password,
                contentDescription = "Password Account"
            )
        },
        placeholder = { Text(text = "Password") },
        value = state.newCredential.password,
        onValueChange = onPasswordChange,
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

@Composable
fun UsernameTextField(
    state: RegistroState,
    onUserNameChange: (String) -> Unit,
) {
    OutlinedTextField(
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = "Account username"
            )
        },
        placeholder = { Text(text = "Username") },
        value = state.newCredential.username,
        onValueChange = onUserNameChange
    )
}

