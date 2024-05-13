package com.example.aplicacionmovilinesmr.ui.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel = hiltViewModel(),
    bottomNavigationBar : @Composable () -> Unit = {},
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    GetPerfilScreen (
        state = state.value,
        bottomNavigationBar = bottomNavigationBar,
    )

}


@Composable
fun GetPerfilScreen(
    state: PerfilState,
    bottomNavigationBar : @Composable () -> Unit = {},
){

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar
    )
    { innerPadding ->
        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    duration = SnackbarDuration.Short,
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
            Row(
                
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
            Text("Aquí iría la pantalla del perfil del usuario")
        }
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {

        }



    }

}