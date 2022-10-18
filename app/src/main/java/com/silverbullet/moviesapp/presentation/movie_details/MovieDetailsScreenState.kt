package com.silverbullet.moviesapp.presentation.movie_details

import com.silverbullet.moviesapp.domain.model.MovieDetails
import com.silverbullet.moviesapp.domain.model.TrailerInfo

data class MovieDetailsScreenState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val movieTrailer: TrailerInfo? = null,
    val error: String? = null
)
