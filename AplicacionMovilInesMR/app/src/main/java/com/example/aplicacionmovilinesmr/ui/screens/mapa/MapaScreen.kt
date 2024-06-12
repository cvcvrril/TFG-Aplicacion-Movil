package com.example.aplicacionmovilinesmr.ui.screens.mapa

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplicacionmovilinesmr.domain.modelo.Ubi
import com.example.aplicacionmovilinesmr.ui.screens.ubicaciones.UbicacionesEvent
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker


@Preview
@Composable
fun MapaScreen(
    viewModel: MapaViewModel = hiltViewModel(),
    bottomNavigationBar: @Composable () -> Unit = {},
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    GetMapaScreen(
        state = state.value,
        bottomNavigationBar = bottomNavigationBar,
        onAdd = {viewModel.handleEvent(MapaEvent.AddUbicacion(Ubi(0, it.longitude, it.latitude, 0)))},
    )
}


@Composable
@SuppressLint("MissingPermission")
fun GetMapaScreen(
    state: MapaState,
    onAdd: (GeoPoint) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar
    ) { innerPadding ->
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
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val DEFAULT_ZOOM = 15

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    MapView(context).apply {
                        setBuiltInZoomControls(true)
                        setMultiTouchControls(true)
                        setTileSource(TileSourceFactory.OpenTopo)
                        isVerticalMapRepetitionEnabled = false
                        isHorizontalMapRepetitionEnabled = false
                        controller.setZoom(DEFAULT_ZOOM)
                        minZoomLevel = DEFAULT_ZOOM.toDouble()
                        tileProvider.tileCache.protectedTileComputers.clear()
                        tileProvider.tileCache.setAutoEnsureCapacity(false)

                        val mapEventsReceiver = object : MapEventsReceiver {
                            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                                val marker = Marker(this@apply)
                                marker.position = p
                                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                this@apply.overlays.add(marker)
                                this@apply.invalidate()

                                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                                val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                                if (location != null) {

                                    Log.i("Ubicacion", "lat: " + location.latitude + "long: " + location.longitude)
                                    val newGeoPoint = GeoPoint(location.latitude, location.longitude)
                                    onAdd(newGeoPoint)
                                }
                                return true
                            }

                            override fun longPressHelper(p: GeoPoint): Boolean {
                                return false
                            }
                        }

                        val mapEventsOverlay = MapEventsOverlay(mapEventsReceiver)
                        this.overlays.add(mapEventsOverlay)
                    }
                },
                update = { view ->
                    view.controller.setCenter(GeoPoint(40.41113131350287, -3.6995493292312234))
                }
            )
        }
    }
}