package com.silverbullet.moviesapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.silverbullet.moviesapp.R
import com.silverbullet.moviesapp.domain.model.toCategory
import com.silverbullet.moviesapp.navigation.Screen
import com.silverbullet.moviesapp.presentation.components.SearchBar
import com.silverbullet.moviesapp.presentation.home.components.*
import com.silverbullet.moviesapp.presentation.ui.theme.BlueAccent
import com.silverbullet.moviesapp.presentation.ui.theme.SoftColor

@ExperimentalPagerApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollState = rememberScrollState()

    // used to pass position to search screen to play an animation for search bar.
    var searchBarPosition by remember {
        mutableStateOf(Offset.Zero)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            TopSection(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                icon = R.drawable.ic_search,
                backgroundColor = SoftColor,
                hint = "Search a title..",
                readOnly = true,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        searchBarPosition = it.positionInRoot()
                    }
                    .onFocusChanged {
                        if (it.isFocused) {
                            navController.navigate(Screen.SearchScreen.route + "/${searchBarPosition.x}/${searchBarPosition.y}")
                        }
                    }
            )
            Slider(
                modifier = Modifier.fillMaxWidth(),
                items = state.trendingMovies,
                onItemClick = { movieId, title ->
                    navController.navigate(Screen.MovieDetailsScreen.route + "/$movieId/$title")
                }
            )
            CategorySelector(
                selectedTextColor = BlueAccent,
                selectedBackgroundColor = SoftColor,
                categories = state.genres.map { it.toCategory() },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            MoviesSection(
                sectionHeader = "Most popular",
                moviesList = state.popularMovies,
                actionString = "Sell All",
                onScrolledToEnd = {
                    if (!state.isLoading) {
                        viewModel.loadPopularMovies()
                    }
                },
                onItemClick = { movieId, title ->
                    navController.navigate(Screen.MovieDetailsScreen.route + "/$movieId/$title")
                }
            )
        }
    }
}