package com.silverbullet.moviesapp.domain.model

import com.silverbullet.moviesapp.data.remote.dto.sub.Genre

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
    val genres: List<Genre>,
    val posterPath: String,
    val backdropPath: String
)