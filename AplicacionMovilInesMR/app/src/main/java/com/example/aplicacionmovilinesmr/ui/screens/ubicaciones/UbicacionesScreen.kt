package com.example.aplicacionmovilinesmr.ui.screens.ubicaciones

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicacionmovilinesmr.R
import com.example.aplicacionmovilinesmr.domain.modelo.dto.UbiDTO


@Composable
fun UbicacionesScreen(
    viewModel: UbicacionesViewModel = hiltViewModel(),
    bottomNavigationBar: @Composable () -> Unit = {},
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    GetUbicaciones(
        state = state.value,
        bottomNavigationBar = bottomNavigationBar
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GetUbicaciones(
    state: UbicacionesState,
    bottomNavigationBar: @Composable () -> Unit = {},
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
        if (state.ubicaciones.isEmpty()) {
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.Gray),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Lista vacía")
                Text("Añada ubicaciones desde el mapa")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(items = state.ubicaciones) { ubicacion ->
                    UbicacionItem(
                        ubi = ubicacion,
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(1000)
                        )
                    )

                }
            }
        }
    }
}


@Composable
fun UbicacionItem(
    ubi: UbiDTO,
    modifier: Modifier = Modifier.background(MaterialTheme.colorScheme.background)
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(id = R.dimen.smallmedium_padding)
            )
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.smallmedium_padding))
        ) {
            Text(
                text = ubi.id.toString(),
                modifier = Modifier.weight(weight = 0.3F),
            )
            Text(
                text = ubi.nombre.ifEmpty { "Ubicación sin nombre" },
                modifier = Modifier.weight(weight = 0.3F),
            )
        }
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.smallmedium_padding))
        ) {
            Text(
                text = ubi.lat.toString(),
                modifier = Modifier.weight(weight = 0.5F)
            )
            Text(
                text = ubi.long.toString(),
                modifier = Modifier.weight(weight = 0.5F)
            )
        }
    }
}


@Preview
@Composable
fun PreviewUbicacionItem() {
    UbicacionItem(
        ubi = UbiDTO(
            1,
            40.43482720277245,
            -3.668364171363615,
            ""
        )
    )

}