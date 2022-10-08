package com.silverbullet.moviesapp.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.moviesapp.domain.model.MovieDetails
import com.silverbullet.moviesapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _favoritesListState = mutableStateOf(emptyList<MovieDetails>())
    val favoritesListState: State<List<MovieDetails>> = _favoritesListState

    init {
        loadFavoriteMoviesList()
    }

    private fun loadFavoriteMoviesList() {
        viewModelScope.launch {
            repository
                .getFavoriteMoviesDetailsList()
                .collectLatest {
                    _favoritesListState.value = it
                }
        }
    }
}