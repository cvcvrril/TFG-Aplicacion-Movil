package com.example.apollo_davidroldan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionmovilinesmr.ui.screens.login.LoginScreen
import com.example.aplicacionmovilinesmr.ui.screens.registro.RegistroScreen
import com.example.aplicacionmovilinesmr.ui.screens.ubicaciones.UbicacionesScreen
import com.example.apollo_davidroldan.ui.common.BottomBar


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
    ) {
        composable(
            "login"
        ) {
            LoginScreen( onLoginDone = {
                navController.navigate("ubicaciones") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            },
                navController = navController)
        }
        composable(
            "registro"
        ) {
            RegistroScreen(navController = navController) {}
        }
        composable(
            "mapa"
        ){

        }
        composable(
            "ubicaciones"
        ){
            UbicacionesScreen(
                navController = navController,
                bottomNavigationBar = {
                    BottomBar(navController = navController,
                        screens = screensBottomBar)
                })

        }
        composable(
            "perfil"
        ){

        }
        composable(
            "config"
        ){

        }
    }
}
