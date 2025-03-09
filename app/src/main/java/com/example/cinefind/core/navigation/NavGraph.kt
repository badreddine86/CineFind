package com.example.cinefind.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinefind.core.utils.AppConstants
import com.example.cinefind.presentation.screens.HomeScreen

@Composable
fun Nav(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination =
        AppConstants.HOME_SCREEN,
        ) {

        composable(route = AppConstants.HOME_SCREEN) {
            HomeScreen()
        }

    }
}