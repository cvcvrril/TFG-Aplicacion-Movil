package com.example.aplicacionmovilinesmr.ui.screens.mapa

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


@Preview
@Composable
fun MapaScreen (
    //viewModel: MapaViewModel = hiltViewModel(),
    bottomNavigationBar: @Composable () -> Unit = {},
){

    //val state = viewModel.uiState.collectAsStateWithLifecycle()

    //var geoPoint by mutableStateOf(GeoPoint(40.41113131350287, -3.6995493292312234))

    //OpenTopo

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            // Creates the view
            MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setBuiltInZoomControls(true)
                setMultiTouchControls(true)
                setTileSource(TileSourceFactory.USGS_TOPO)
                setTileSource(TileSourceFactory.CLOUDMADESMALLTILES)
                tileProvider.tileCache.protectedTileComputers.clear()
                tileProvider.tileCache.setAutoEnsureCapacity(false)
                setOnClickListener {
                    TODO("Handle click here")
                }
            }
        },
        update = { view ->
            // Code to update or recompose the view goes here
            // Since geoPoint is read here, the view will recompose whenever it is updated
            view.controller.setCenter(GeoPoint(40.41113131350287, -3.6995493292312234))
        }
    )

}

