package com.silverbullet.moviesapp.presentation.home

import com.silverbullet.moviesapp.domain.model.Genre
import com.silverbullet.moviesapp.domain.model.MovieInfo

data class HomeState(
    val genres: List<Genre> = emptyList(),
    val selectedGenre: Genre? = null,
    val trendingMovies: List<MovieInfo> = emptyList(),
    val popularMovies: List<MovieInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)