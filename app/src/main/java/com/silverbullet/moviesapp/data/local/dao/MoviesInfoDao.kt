package com.silverbullet.moviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.silverbullet.moviesapp.data.local.entity.MovieInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(moviesInfoList: List<MovieInfoEntity>)

    @Query("SELECT * from movies_info_table")
    fun getMoviesInfo(): Flow<List<MovieInfoEntity>>

    @Query("DELETE FROM movies_info_table")
    suspend fun clear()
}