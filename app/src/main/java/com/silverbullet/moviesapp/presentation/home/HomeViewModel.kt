package com.silverbullet.moviesapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.moviesapp.domain.repository.MoviesRepository
import com.silverbullet.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private var currentPage = 1

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        loadGenres()
        loadPopularMovies()
        loadTrendingMovies()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            repository
                .getMoviesGenres()
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let { genres ->
                                _state.value = _state.value.copy(genres = genres, isLoading = false)
                            }
                        }
                        is Resource.Error -> {
                            _state.value =
                                _state.value.copy(error = resource.message, isLoading = false)
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = null)
                        }
                    }
                }
        }
    }

    private fun loadPopularMovies() {
        viewModelScope.launch {
            repository
                .getPopularMoves(currentPage)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let { moviesInfo ->
                                _state.value = _state.value.copy(
                                    popularMovies = moviesInfo,
                                    isLoading = resource.isLoading
                                )
                            }
                            currentPage++
                        }
                        is Resource.Error -> {
                            _state.value =
                                _state.value.copy(error = resource.message, isLoading = false)
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = null)
                        }
                    }
                }
        }
    }

    private fun loadTrendingMovies() {
        viewModelScope.launch {
            repository
                .getTrendingMovies()
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let { trendingMovies ->
                                _state.value = _state.value.copy(
                                    trendingMovies = trendingMovies,
                                    isLoading = resource.isLoading
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.value =
                                _state.value.copy(error = resource.message, isLoading = false)
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = null)
                        }
                    }
                }
        }
    }

}