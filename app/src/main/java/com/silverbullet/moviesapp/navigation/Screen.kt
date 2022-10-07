package com.silverbullet.moviesapp.navigation

sealed class Screen(val route: String) {

    object HomeScreen : Screen("home_screen_route")
    object SearchScreen: Screen("search_screen_route")
    object FavoriteScreen: Screen("favorite_screen_route")
    object MovieDetailsScreen: Screen("movie_details_screen")
}
