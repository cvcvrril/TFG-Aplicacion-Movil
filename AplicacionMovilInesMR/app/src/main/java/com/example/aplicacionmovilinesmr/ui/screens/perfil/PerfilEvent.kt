package com.example.aplicacionmovilinesmr.ui.screens.perfil

import android.net.Uri

sealed class PerfilEvent {

    object ErrorVisto : PerfilEvent()
    object UpdatePerfil : PerfilEvent()
    class OnEditModeChange(val editmode: Boolean) : PerfilEvent()
    class OnNombreCompletoChange(val nombreCompleto: String) : PerfilEvent()
    class OnDescripciionChange(val descripcion: String) : PerfilEvent()
    class OnImagenChange(val imageUri: Uri) : PerfilEvent()

}