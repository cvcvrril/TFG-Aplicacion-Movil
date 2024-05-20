package com.example.aplicacionmovilinesmr.ui.screens.configscreen

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ConfigScreen() {
}

@Composable
fun CambioTemaCheckbox() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Cambiar el tema de la aplicación")
        Checkbox(checked = false, onCheckedChange = {

        }
        )

    }
}


@Preview
@Composable
fun DialogoAlertaLogout(){
    AlertDialog(
        title = {
            Text(text = "¿Seguro que quiere cerrar sesión?")
        },
        onDismissRequest = {
            //onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    //onConfirmation()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    //onDismissRequest()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Preview
@Composable
fun previewConfigScreen() {
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
            CambioTemaCheckbox()
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
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Sobre la aplicación")
            }
        }

    }

}