package com.silverbullet.moviesapp.data.remote.dto

import com.silverbullet.moviesapp.domain.model.Genre

data class GenresResponse(
    val genres: List<Genre>
)