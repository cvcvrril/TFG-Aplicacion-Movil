package com.example.aplicacionmovilinesmr.ui.screens.ubicaciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.UserManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Composable
fun UbicacionesScreen(
    viewModel: UbicacionesViewModel = hiltViewModel(),
    bottomNavigationBar : @Composable () -> Unit = {},
    ) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    GetUbicaciones(
        state = state.value,
        bottomNavigationBar = bottomNavigationBar
    )

}

@Composable
fun GetUbicaciones(
    state: UbicacionesState,
    bottomNavigationBar : @Composable () -> Unit = {},
) {

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
            Text("Hola")
        }


    }
}