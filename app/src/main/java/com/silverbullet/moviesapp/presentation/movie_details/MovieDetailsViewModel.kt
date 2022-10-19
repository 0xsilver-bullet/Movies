package com.silverbullet.moviesapp.presentation.movie_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.moviesapp.domain.repository.MoviesRepository
import com.silverbullet.moviesapp.utils.Constants.MOVIE_ID_KEY
import com.silverbullet.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MoviesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(MovieDetailsScreenState())
    val state: State<MovieDetailsScreenState> = _state

    init {
        Timber.d("Initializing...")
        val movieId = savedStateHandle.get<Int>(MOVIE_ID_KEY)
        movieId?.let {
            loadMovieDetails(movieId)
            loadMovieTrailer(movieId)
        }
    }

    fun toggleFavorite() {
        _state.value.movieDetails?.let { movieDetails ->
            viewModelScope.launch {
                repository.updateMovieDetails(
                    movieDetails = movieDetails.copy(
                        favorite = !movieDetails.favorite
                    )
                )
                loadMovieDetails(movieDetails.id)
            }
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository
                .getMovieDetails(movieId = movieId)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                _state.value = _state.value.copy(
                                    movieDetails = it,
                                    isLoading = resource.isLoading
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.value =
                                _state.value.copy(isLoading = false, error = resource.message)
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = null)
                        }
                    }
                }
        }
    }

    private fun loadMovieTrailer(movieId: Int) {
        viewModelScope.launch {
            repository
                .getMovieTrailer(movieId = movieId)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(movieTrailer = resource.data)
                        }
                        is Resource.Error -> {
                            Timber.d("Failed to load movie trailer")
                        }
                        is Resource.Loading -> {
                            Timber.d("Loading Movie Trailer")
                        }
                    }
                }
        }
    }
}