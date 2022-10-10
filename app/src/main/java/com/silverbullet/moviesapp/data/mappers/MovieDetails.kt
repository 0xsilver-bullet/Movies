package com.silverbullet.moviesapp.data.mappers

import com.silverbullet.moviesapp.data.local.entity.MovieDetailsEntity
import com.silverbullet.moviesapp.data.remote.dto.MovieDetailsDto
import com.silverbullet.moviesapp.domain.model.MovieDetails

fun MovieDetailsDto.toMoveDetailsEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        adult = adult,
        genres = genres,
        posterPath = posterPath,
        backdropPath = backdropPath,
        timeInMinutes = runtime,
        voteAverage = voteAverage
    )
}

fun MovieDetailsEntity.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        adult = adult,
        genres = genres,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        timeInMinutes = timeInMinutes,
        favorite = favorite
    )
}

fun MovieDetails.toMovieDetailsEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        adult = adult,
        genres = genres,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        timeInMinutes = timeInMinutes,
        favorite = favorite
    )
}