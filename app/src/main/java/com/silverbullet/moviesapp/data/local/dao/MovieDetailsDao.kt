package com.silverbullet.moviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.silverbullet.moviesapp.data.local.entity.MovieDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(moviesDetails: MovieDetailsEntity)

    @Query("SELECT * FROM movies_details_table WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieDetailsEntity?

    @Query("SELECT * FROM movies_details_table WHERE favorite = 1")
    fun getFavoriteMoviesList(): Flow<List<MovieDetailsEntity>>
}