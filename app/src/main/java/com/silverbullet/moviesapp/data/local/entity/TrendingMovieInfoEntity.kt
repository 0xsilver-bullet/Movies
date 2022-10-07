package com.silverbullet.moviesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_movies_table")
data class TrendingMovieInfoEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val adult: Boolean,
    val posterPath: String,
    val backdropPath: String,
    val genreIds: List<Int>,
    val voteAverage: Double
)