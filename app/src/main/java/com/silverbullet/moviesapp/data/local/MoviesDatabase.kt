package com.silverbullet.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.silverbullet.moviesapp.data.local.dao.MovieDetailsDao
import com.silverbullet.moviesapp.data.local.dao.MoviesInfoDao
import com.silverbullet.moviesapp.data.local.entity.MovieDetailsEntity
import com.silverbullet.moviesapp.data.local.entity.MovieInfoEntity

@Database(
    entities = [
        MovieDetailsEntity::class,
        MovieInfoEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val moviesInfoDao: MoviesInfoDao
    abstract val movieDetailsDao: MovieDetailsDao
}