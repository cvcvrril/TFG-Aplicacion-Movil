package com.example.apollo_davidroldan.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Movie
import androidx.compose.ui.graphics.vector.ImageVector


val screensBottomBar = listOf(
    Screens("", Icons.Filled.Movie),
    Screens("", Icons.Filled.Celebration),
)

data class Screens(val route: String, val icon: ImageVector) {

}
