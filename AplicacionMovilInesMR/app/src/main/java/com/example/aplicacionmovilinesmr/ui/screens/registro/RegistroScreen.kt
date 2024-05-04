package com.example.aplicacionmovilinesmr.ui.screens.registro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.reflect.KFunction1

@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: RegistroViewModel = hiltViewModel(),
    onRegistro: () -> Unit,
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    OnRegistro(
        state = state.value,
        handleEvent = viewModel::handleEvent,
        navController = navController,
        registro = { viewModel.handleEvent(RegistroEvent.Registro()) },
        onUserNameChange = { viewModel.handleEvent(RegistroEvent.OnUserNameChange(it)) },
        onPasswordChange = { viewModel.handleEvent(RegistroEvent.OnPasswordChange(it)) },
    )

}

@Composable
fun OnRegistro(
    navController: NavController,
    state: RegistroState,
    registro: () -> Unit,
    handleEvent: KFunction1<RegistroEvent, Unit>,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
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
                .fillMaxSize()
                .background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Registro")
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                placeholder = { Text(text = "Username") },
                value = state.newCredential.username,
                onValueChange = onUserNameChange
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                placeholder = { Text(text = "Password") },
                value = state.newCredential.password,
                onValueChange = { valueIntroduced ->
                    handleEvent(RegistroEvent.OnPasswordChange(valueIntroduced))
                })
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                placeholder = { Text(text = "Nombre Completo") },
                value = state.newCredential.nombreCompleto,
                onValueChange = { valueIntroduced ->
                    handleEvent(RegistroEvent.OnNombreCompletoChange(valueIntroduced))
                })
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                placeholder = { Text(text = "Email") },
                value = state.newCredential.email,
                onValueChange = { valueIntroduced ->
                    handleEvent(RegistroEvent.OnEmailChange(valueIntroduced))
                })
            Spacer(modifier = Modifier.height(30.dp))
            CustomDatePicker(
                state = state,
                handleEvent = handleEvent)
            Button(
                onClick = registro
            ) {
                Text(text = "Registro")
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text("¿Tienes ya una cuenta?")
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    navController.navigate("login")
                }) {
                Text(text = "Ir al Login")
            }
        }

    }
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
        TextField(
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
        onDismissRequest = {  },
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

