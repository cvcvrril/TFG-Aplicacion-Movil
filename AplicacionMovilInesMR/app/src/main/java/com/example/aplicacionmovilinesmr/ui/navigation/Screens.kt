package com.example.apollo_davidroldan.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.apollo_davidroldan.ui.common.ConstantesPantallas


val screensBottomBar = listOf(
    Screens(ConstantesPantallas.PERFIL_PANTALLA, Icons.Filled.AccountCircle),
    Screens(ConstantesPantallas.MAPA_PANTALLA, Icons.Filled.Map),
    Screens(ConstantesPantallas.UBICACIONES_PANTALLA, Icons.Filled.Place),
)

data class Screens(val route: String, val icon: ImageVector) {

}
