package com.silverbullet.moviesapp.data.mappers

import com.silverbullet.moviesapp.data.local.entity.MovieInfoEntity
import com.silverbullet.moviesapp.data.remote.dto.MovieInfoDto

fun MovieInfoDto.toMovieInfoEntity(): MovieInfoEntity {
    return MovieInfoEntity(
        id = id,
        adult = adult,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genreIds = genreIds,
        voteAverage = voteAverage
    )
}