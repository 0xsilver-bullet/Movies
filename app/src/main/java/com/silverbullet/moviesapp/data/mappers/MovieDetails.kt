package com.silverbullet.moviesapp.data.mappers

import com.silverbullet.moviesapp.data.local.entity.MovieDetailsEntity
import com.silverbullet.moviesapp.data.remote.dto.MovieDetailsDto

fun MovieDetailsDto.toMoveDetailsEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        adult = adult,
        genres = genres,
        posterPath = posterPath,
        backdropPath = backdropPath
    )
}