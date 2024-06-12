package com.example.aplicacionmovilinesmr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionmovilinesmr.ui.screens.configscreen.ConfigScreen
import com.example.aplicacionmovilinesmr.ui.screens.forgotPassword.ForgotPasswordScreen
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
                toRegistro = { navController.navigate(ConstantesPantallas.REGISTRO_PANTALLA) },
                toForgotPassword = { navController.navigate(ConstantesPantallas.FORGOTPASSWORD_PANTALLA) })
        }
        composable(
            ConstantesPantallas.REGISTRO_PANTALLA
        ) {
            RegistroScreen(
                toLogin = {
                    navController.navigate(ConstantesPantallas.LOGIN_PANTALLA)
                }
            )
        }
        composable(
            ConstantesPantallas.FORGOTPASSWORD_PANTALLA
        ){
            ForgotPasswordScreen(
                toLogin = {
                    navController.navigate(ConstantesPantallas.LOGIN_PANTALLA)
                }
            )
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
