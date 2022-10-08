package com.silverbullet.moviesapp.presentation.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.silverbullet.moviesapp.navigation.Screen
import com.silverbullet.moviesapp.presentation.components.DetailsMovieItem

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteMoviesList = viewModel.favoritesListState.value

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Favorites",
            style = MaterialTheme.typography.h4,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            items(favoriteMoviesList) { movieDetails ->
                DetailsMovieItem(
                    movieDetails = movieDetails,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.MovieDetailsScreen.route + "/${movieDetails.id}/${movieDetails.title}")
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}