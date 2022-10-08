package com.silverbullet.moviesapp.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
    val genres: List<Genre>,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val favorite: Boolean = false
)