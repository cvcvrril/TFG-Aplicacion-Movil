package com.example.aplicacionmovilinesmr.ui.screens.configscreen

data class ConfigState (
    val error: String? = null,
    val loading: Boolean = false,
    var shouldShowDialogAbout: Boolean = false,
    var shouldShowDialogLogout: Boolean = false,
    var shouldShowDialogBaja: Boolean = false,
)