package com.example.aplicacionmovilinesmr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionmovilinesmr.ui.screens.configscreen.ConfigScreen
import com.example.aplicacionmovilinesmr.ui.screens.login.LoginScreen
import com.example.aplicacionmovilinesmr.ui.screens.mapa.MapaScreen
import com.example.aplicacionmovilinesmr.ui.screens.perfil.PerfilScreen
import com.example.aplicacionmovilinesmr.ui.screens.registro.RegistroScreen
import com.example.aplicacionmovilinesmr.ui.screens.ubicaciones.UbicacionesScreen
import com.example.apollo_davidroldan.ui.common.BottomBar
import com.example.apollo_davidroldan.ui.common.ConstantesPantallas
import com.example.apollo_davidroldan.ui.navigation.screensBottomBar


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ConstantesPantallas.LOGIN_PANTALLA,
    ) {
        composable(
            ConstantesPantallas.LOGIN_PANTALLA
        ) {
            LoginScreen( onLoginDone = {
                navController.navigate(ConstantesPantallas.MAPA_PANTALLA) {
                    popUpTo(ConstantesPantallas.LOGIN_PANTALLA) {
                        inclusive = true
                    }
                }
            },
                navController = navController)
        }
        composable(
            ConstantesPantallas.REGISTRO_PANTALLA
        ) {
            RegistroScreen(navController = navController) {}
        }
        composable(
            ConstantesPantallas.MAPA_PANTALLA
        ){
            MapaScreen(
                bottomNavigationBar = {
                    BottomBar(navController = navController,
                        screens = screensBottomBar
                    )
                })
        }
        composable(
            ConstantesPantallas.UBICACIONES_PANTALLA
        ){
            UbicacionesScreen(
                bottomNavigationBar = {
                    BottomBar(navController = navController,
                        screens = screensBottomBar
                    )
                })

        }
        composable(
            ConstantesPantallas.PERFIL_PANTALLA
        ){
            PerfilScreen (
                toConfig = {navController.navigate(ConstantesPantallas.CONFIG_PANTALLA)},
                bottomNavigationBar = {
                    BottomBar(navController = navController,
                        screens = screensBottomBar
                    )
                }
            )
        }
        composable(
            ConstantesPantallas.CONFIG_PANTALLA
        ){
            ConfigScreen(
                toLogout = {navController.navigate(ConstantesPantallas.LOGIN_PANTALLA)}
            )
        }
    }
}
