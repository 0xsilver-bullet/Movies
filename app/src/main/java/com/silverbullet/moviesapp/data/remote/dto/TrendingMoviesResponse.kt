package com.silverbullet.moviesapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TrendingMoviesResponse(
    val page: Int,
    val results: List<MovieInfoDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)