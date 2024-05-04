package com.example.apollo_davidroldan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aplicacionmovilinesmr.ui.screens.login.LoginScreen
import com.example.aplicacionmovilinesmr.ui.screens.registro.RegistroScreen


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
            LoginScreen( onLogin = {
                navController.navigate("") {
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
            RegistroScreen(navController = navController) {
                
            }
        }
    }
}
