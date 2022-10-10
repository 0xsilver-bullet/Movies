package com.silverbullet.moviesapp.domain.model

data class SearchResult(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val movies: List<MovieInfo>,
    val actors: List<Actor>
)
