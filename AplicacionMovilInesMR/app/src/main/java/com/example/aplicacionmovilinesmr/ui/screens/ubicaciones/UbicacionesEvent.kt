package com.example.aplicacionmovilinesmr.ui.screens.ubicaciones

sealed class UbicacionesEvent {

    data class GetUbicaciones(val id:Int) : UbicacionesEvent()
    class DeleteUbicacion(val id: Int) : UbicacionesEvent()
    object ErrorVisto : UbicacionesEvent()

}