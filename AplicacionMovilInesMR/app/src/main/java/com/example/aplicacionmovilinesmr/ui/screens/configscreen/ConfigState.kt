package com.example.aplicacionmovilinesmr.ui.screens.configscreen

/**
 * tema = false -> Dark Mode
 * tema = true -> Light Mode
 * **/

data class ConfigState (
    val error: String? = null,
    val loading: Boolean = false,
    val tema: Boolean = false,
    var shouldShowDialogAbout: Boolean = false,
    var shouldShowDialogLogout: Boolean = false,
    var shouldShowDialogBaja: Boolean = false,
)