package com.silverbullet.moviesapp.navigation

sealed class Screen(val route: String) {

    object HomeScreen : Screen("home_screen_route")

}
