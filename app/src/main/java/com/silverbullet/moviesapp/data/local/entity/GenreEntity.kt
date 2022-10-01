package com.silverbullet.moviesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres_table")
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
)
