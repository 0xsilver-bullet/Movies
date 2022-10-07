package com.silverbullet.moviesapp.domain.model

data class MovieInfo(
    val id: Int,
    val title: String,
    val adult: Boolean,
    val posterPath: String,
    val backdropPath: String,
    val genreIds: List<Int>,
    val voteAverage: Double
)
