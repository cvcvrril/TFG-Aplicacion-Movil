package com.example.aplicacionmovilinesmr.ui.screens.configscreen

sealed class ConfigEvent {

    object DoLogout : ConfigEvent()
    object DoBaja : ConfigEvent()
    object ErrorVisto : ConfigEvent()
}