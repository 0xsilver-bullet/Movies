package com.silverbullet.moviesapp.domain.model

data class Category(
    val id: Int,
    val name: String
)

fun Genre.toCategory(): Category{
    return Category(
        id = id,
        name = name
    )
}
