package com.silverbullet.moviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.silverbullet.moviesapp.data.local.entity.TrendingMovieInfoEntity

@Dao
interface TrendingMoviesInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(moviesInfo: List<TrendingMovieInfoEntity>)

    @Query("SELECT * FROM trending_movies_table")
    suspend fun getMovies(): List<TrendingMovieInfoEntity>

    @Query("DELETE FROM trending_movies_table")
    suspend fun clear()
}