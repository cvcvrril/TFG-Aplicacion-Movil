package com.example.aplicacionmovilinesmr.ui.screens.configscreen

sealed class ConfigEvent {

    object DoLogout : ConfigEvent()
    object ErrorVisto : ConfigEvent()
    class OnTemaChange(val tema: Boolean) : ConfigEvent()

}