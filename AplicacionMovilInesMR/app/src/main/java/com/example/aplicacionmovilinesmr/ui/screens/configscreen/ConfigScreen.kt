package com.example.aplicacionmovilinesmr.ui.screens.configscreen

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.tooling.preview.Preview
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
        handleEvent = viewModel::handleEvent,
        doLogout = { viewModel.handleEvent(ConfigEvent.DoLogout) },
        toLogout = toLogout,
    )

}

@Composable
fun GetConfigScreen(
    state: ConfigState,
    handleEvent: KFunction1<ConfigEvent, Unit>,
    doLogout: () -> Unit,
    toLogout: () -> Unit
) {

    var shouldShowDialogLogout by remember { mutableStateOf(false) }
    var shouldShowDialogBaja by remember { mutableStateOf(false) }
    var shouldShowDialogAbout by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(end = 16.dp, top = 10.dp, start = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        ) {
            CambioTemaCheckbox(
                handleEvent = handleEvent,
                state = state,
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        ) {
            Text(text = "Hola, prueba")
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        ) {
            Text(text = "Esto es otra prueba")
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
        Row {
            TextButton(onClick = { shouldShowDialogAbout = true }) {
                Text(text = "Sobre la aplicación")
            }
        }

        if (shouldShowDialogLogout) {
            DialogoAlertaLogout(
                onDismissRequest = { shouldShowDialogLogout = false },
                onConfirmation = {
                    shouldShowDialogLogout = false
                    toLogout()
                    doLogout()
                },
            )
        }

        if (shouldShowDialogAbout) {
            AboutTheAppDialog(
                onDismissRequest = {shouldShowDialogAbout = false }
            )
        }

        if (shouldShowDialogBaja) {
            DialogoAlertaBaja(
                onDismissRequest = {shouldShowDialogLogout = false },
                onConfirmation = {
                    shouldShowDialogLogout = false
                    toLogout()
                    doLogout()
                },
            )
        }
    }
}

@Composable
fun CambioTemaCheckbox(
    state: ConfigState,
    handleEvent: KFunction1<ConfigEvent, Unit>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Cambiar el tema de la aplicación")
        Checkbox(checked = state.tema, onCheckedChange = { change ->
            handleEvent(ConfigEvent.OnTemaChange(change))
        }
        )
    }
}


@Composable
fun DialogoAlertaLogout(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    //toLogout: () -> Unit,
    //doLogout: () -> Unit,
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
    //toLogout: () -> Unit,
    //doLogout: () -> Unit,
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
            /*TODO: pasar el método para dar de baja la cuenta*/
            //toLogout()
            //doLogout
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

@Preview
@Composable
fun previewConfigScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(end = 16.dp, top = 10.dp, start = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        ) {
            //CambioTemaCheckbox()
        }
        Row(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        ) {
            Text(text = "Hola, prueba")
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        ) {
            Text(text = "Esto es otra prueba")
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Cerrar sesión",
                    color = Color.Red
                )
            }
        }
        Row {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Darte de baja",
                    color = Color.Red
                )
            }
        }
        Row {
            TextButton(onClick = {
                //shouldShowDialogAbout.value = true
            }) {
                Text(text = "Sobre la aplicación")
            }
        }

    }

}