package com.example.aplicacionmovilinesmr.ui.screens.mapa

import com.example.aplicacionmovilinesmr.domain.modelo.dto.UbiDTO
import org.osmdroid.util.GeoPoint

data class MapaState(
    val geoPoint : GeoPoint? = null,
    val ubicaciones : List<UbiDTO>? = null,
    val error: String? = null,
    val loading: Boolean = false,

    )