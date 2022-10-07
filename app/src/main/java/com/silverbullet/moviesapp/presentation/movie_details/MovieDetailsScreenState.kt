package com.silverbullet.moviesapp.presentation.movie_details

import com.silverbullet.moviesapp.domain.model.MovieDetails

data class MovieDetailsScreenState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val error: String? = null
)
