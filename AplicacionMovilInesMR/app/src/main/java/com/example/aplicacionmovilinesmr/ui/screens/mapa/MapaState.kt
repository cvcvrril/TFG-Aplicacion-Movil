package com.example.aplicacionmovilinesmr.ui.screens.mapa

import org.osmdroid.util.GeoPoint

data class MapaState(
    val geoPoint : GeoPoint? = null,
    val error: String? = null,
    val loading: Boolean = false,

)