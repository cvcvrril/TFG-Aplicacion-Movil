package com.example.apollo_davidroldan.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector


val screensBottomBar = listOf(
    Screens("perfil", Icons.Filled.AccountCircle),
    Screens("mapa", Icons.Filled.Map),
    Screens("ubicaciones", Icons.Filled.Place),
)

data class Screens(val route: String, val icon: ImageVector) {

}
