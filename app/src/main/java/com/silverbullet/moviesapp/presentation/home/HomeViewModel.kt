package com.silverbullet.moviesapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverbullet.moviesapp.domain.model.Genre
import com.silverbullet.moviesapp.domain.model.MovieInfo
import com.silverbullet.moviesapp.domain.repository.MoviesRepository
import com.silverbullet.moviesapp.utils.Constants.GENRE_SHOW_ALL_ID
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

    private val _selectedGenreId = mutableStateOf(GENRE_SHOW_ALL_ID)
    val selectedGenreId: State<Int> = _selectedGenreId
    private var cachedPopularMoviesList: List<MovieInfo>? = null

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        loadGenres()
        loadPopularMovies()
        loadTrendingMovies()
    }

    fun onGenreSelected(genreId: Int) {
        _selectedGenreId.value= genreId
        filterByGenreAndUpdate()
    }

    /**
     * Filters the cachedMoviesInfoList by currentSelectedGenre and then updates the state with filtered list
     */
    private fun filterByGenreAndUpdate() {
        if (selectedGenreId.value == GENRE_SHOW_ALL_ID) {
            // Then show all is selected, no need to apply filter
            _state.value = _state.value.copy(
                popularMovies = cachedPopularMoviesList ?: _state.value.popularMovies
            )
            return
        }
        val filteredList = cachedPopularMoviesList?.filter {
            it.genreIds.contains(selectedGenreId.value)
        }
        _state.value = _state.value.copy(popularMovies = filteredList ?: _state.value.popularMovies)
    }

    private fun loadGenres() {
        viewModelScope.launch {
            repository
                .getMoviesGenres()
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let { genres ->
                                // Modified Genre List adds the option All which is not included by default in the API.
                                val modifiedGenreList =
                                    mutableListOf(Genre(GENRE_SHOW_ALL_ID, "All"))
                                modifiedGenreList.addAll(genres)
                                _state.value = _state.value.copy(
                                    genres = modifiedGenreList.toList(),
                                    isLoading = false
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

    fun loadPopularMovies() {
        viewModelScope.launch {
            repository
                .getPopularMoves(currentPage)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let { moviesInfo ->
                                _state.value = _state.value.copy(isLoading = resource.isLoading)
                                cachedPopularMoviesList = buildList {
                                    addAll(_state.value.popularMovies)
                                    addAll(moviesInfo)
                                }.distinctBy { it.id }
                                filterByGenreAndUpdate()
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