package com.example.aplicacionmovilinesmr.ui.screens.mapa

import com.example.aplicacionmovilinesmr.domain.modelo.Ubi

sealed class MapaEvent {

    data class GetUbicaciones(val id:Int) : MapaEvent()
    class AddUbicacion(val newUbi : Ubi) : MapaEvent()
    object ErrorVisto : MapaEvent()


}