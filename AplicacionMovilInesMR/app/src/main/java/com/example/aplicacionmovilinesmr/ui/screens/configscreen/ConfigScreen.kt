package com.example.aplicacionmovilinesmr.ui.screens.configscreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.reflect.KFunction1


@Composable
fun ConfigScreen(
    viewModel: ConfigViewModel = hiltViewModel(),
    toLogout: () -> Unit,
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()




    GetConfigScreen(
        state = state.value,
        doLogout = { viewModel.handleEvent(ConfigEvent.DoLogout) },
        doBaja = { viewModel.handleEvent(ConfigEvent.DoBaja) },
        toLogout = toLogout,
    )

}

@Composable
fun GetConfigScreen(
    state: ConfigState,
    doLogout: () -> Unit,
    doBaja: () -> Unit,
    toLogout: () -> Unit,
) {

    var shouldShowDialogLogout by remember { mutableStateOf(false) }
    var shouldShowDialogBaja by remember { mutableStateOf(false) }
    var shouldShowDialogAbout by remember { mutableStateOf(false) }
    var openUri by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 16.dp, top = 10.dp, start = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Account")
            Text(text = "Acerca de la cuenta")
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row {
            TextButton(onClick = { shouldShowDialogLogout = true }) {
                Text(
                    text = "Cerrar sesión",
                    color = Color.Red
                )
            }
        }
        Row {
            TextButton(onClick = { shouldShowDialogBaja = true }) {
                Text(
                    text = "Darte de baja",
                    color = Color.Red
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.Android, contentDescription = "Account")
            Text(text = "Acerca de la aplicación")
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row {
            TextButton(onClick = { shouldShowDialogAbout = true }) {
                Text(text = "Sobre la aplicación")
            }
        }
        Row {
            TextButton(onClick = { openUri = true }) {
                Text(text = "Documentación")
            }
        }

        if (openUri){
            PantallaUriDoc()
        }

        if (shouldShowDialogLogout) {
            DialogoAlertaLogout(
                onDismissRequest = { shouldShowDialogLogout = false },
                onConfirmation = {
                    shouldShowDialogLogout = false
                    doLogout()
                    toLogout()
                },
            )
        }

        if (shouldShowDialogBaja) {
            DialogoAlertaBaja(
                onDismissRequest = { shouldShowDialogLogout = false },
                onConfirmation = {
                    shouldShowDialogLogout = false
                    doBaja()
                    toLogout()
                },
            )
        }

        if (shouldShowDialogAbout) {
            AboutTheAppDialog(
                onDismissRequest = { shouldShowDialogAbout = false }
            )
        }
    }
}

@Composable
fun PantallaUriDoc(){
    val localUriHandler = LocalUriHandler.current
    localUriHandler.openUri("https://cvcvrril.github.io/MapEatDocs/")

}

@Composable
fun DialogoAlertaLogout(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = "¿Seguro que quiere cerrar sesión?")
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun DialogoAlertaBaja(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = "¿Seguro que quiere darse de baja?")
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun AboutTheAppDialog(
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Desarrollado por: Inés Martínez",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Versión de la aplicación: Beta 1.4.0",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Realizado con mucha paciencia.",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cerrar")
                }
            }
        }
    }
}