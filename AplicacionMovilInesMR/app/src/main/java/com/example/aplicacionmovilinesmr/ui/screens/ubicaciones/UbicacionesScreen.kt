package com.example.aplicacionmovilinesmr.ui.screens.ubicaciones

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicacionmovilinesmr.R
import com.example.aplicacionmovilinesmr.domain.modelo.dto.UbiDTO
import kotlinx.coroutines.delay


@Composable
fun UbicacionesScreen(
    viewModel: UbicacionesViewModel = hiltViewModel(),
    bottomNavigationBar: @Composable () -> Unit = {},
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    GetUbicaciones(
        state = state.value,
        bottomNavigationBar = bottomNavigationBar,
        onDelete = { viewModel.handleEvent(UbicacionesEvent.DeleteUbicacion(it)) },
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GetUbicaciones(
    state: UbicacionesState,
    onDelete: (Int) -> Unit,
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
                items(
                    items = state.ubicaciones,
                    key = { item -> item.id }
                ) { ubicacion ->
                    SwipeToDeleteContainer(
                        item = ubicacion,
                        onDelete = {
                            it.id?.let{it1 -> onDelete(it1)}
                        }) { ubicacion ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> SwipeToDeleteContainer(
    item: T,
    onDelete: (UbiDTO) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val dismissState = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                onDelete(item)
                true
            } else {
                false
            }
        }
    )

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = dismissState,
            background = {
                DeleteBackground(swippeDismissValue = dismissState)
            },
            dismissContent = {
                content(item)
            },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swippeDismissValue: DismissState
) {
    val color = if (swippeDismissValue.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White,
        )

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