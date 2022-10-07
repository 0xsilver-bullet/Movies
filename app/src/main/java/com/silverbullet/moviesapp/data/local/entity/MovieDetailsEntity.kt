package com.silverbullet.moviesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.silverbullet.moviesapp.domain.model.Genre

@Entity(tableName = "movies_details_table")
data class MovieDetailsEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
    val genres: List<Genre>,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double
)