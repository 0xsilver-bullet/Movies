package com.silverbullet.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.silverbullet.moviesapp.presentation.favorites.FavoritesScreen
import com.silverbullet.moviesapp.presentation.home.HomeScreen
import com.silverbullet.moviesapp.presentation.movie_details.MovieDetailsScreen
import com.silverbullet.moviesapp.presentation.search.SearchScreen
import com.silverbullet.moviesapp.utils.Constants.MOVIE_ID_KEY
import com.silverbullet.moviesapp.utils.Constants.MOVIE_TITLE_KEY
import com.silverbullet.moviesapp.utils.Constants.OFFSET_X_KEY
import com.silverbullet.moviesapp.utils.Constants.OFFSET_Y_KEY
import timber.log.Timber

@ExperimentalPagerApi
@Composable
fun MoviesAppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(
            Screen.MovieDetailsScreen.route + "/{$MOVIE_ID_KEY}/{$MOVIE_TITLE_KEY}",
            arguments = listOf(
                navArgument(MOVIE_ID_KEY) {
                    type = NavType.IntType
                },
                navArgument(MOVIE_TITLE_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            val movieTitle = it.arguments?.getString(MOVIE_TITLE_KEY)
            MovieDetailsScreen(
                navController = navController,
                movieTitle = movieTitle ?: ""
            )
        }

        composable(
            Screen.SearchScreen.route + "/{$OFFSET_X_KEY}/{$OFFSET_Y_KEY}",
            arguments = listOf(
                navArgument(OFFSET_X_KEY) {
                    type = NavType.FloatType
                    defaultValue = 0f
                },
                navArgument(OFFSET_Y_KEY) {
                    type = NavType.FloatType
                    defaultValue = 0f
                }
            )
        ) {
            val xOffset = it.arguments?.getFloat(OFFSET_X_KEY)!!
            val yOffset = it.arguments?.getFloat(OFFSET_Y_KEY)!!
            Timber.d("xOffset: $xOffset yOffset: $yOffset")
            SearchScreen(
                navController = navController,
                offset = Offset(xOffset, yOffset)
            )
        }

        composable(Screen.FavoriteScreen.route) {
            FavoritesScreen(navController)
        }
    }
}