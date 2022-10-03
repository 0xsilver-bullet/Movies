package com.silverbullet.moviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.silverbullet.moviesapp.data.local.entity.GenreEntity

@Dao
interface GenresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<GenreEntity>)

    @Query("SELECT * FROM genres_table")
    suspend fun getGenres(): List<GenreEntity>
}