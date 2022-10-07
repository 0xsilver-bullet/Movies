package com.silverbullet.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.silverbullet.moviesapp.presentation.home.HomeScreen

@ExperimentalPagerApi
@Composable
fun MoviesAppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
    }
}