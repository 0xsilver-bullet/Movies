package com.silverbullet.moviesapp.data.mappers

import com.silverbullet.moviesapp.data.local.entity.GenreEntity
import com.silverbullet.moviesapp.domain.model.Genre

fun GenreEntity.toGenre(): Genre {
    return Genre(id = id, name = name)
}

fun Genre.toGenreEntity(): GenreEntity {
    return GenreEntity(id = id, name = name)
}