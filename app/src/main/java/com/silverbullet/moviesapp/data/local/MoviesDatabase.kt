package com.silverbullet.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.silverbullet.moviesapp.data.local.dao.GenresDao
import com.silverbullet.moviesapp.data.local.dao.MovieDetailsDao
import com.silverbullet.moviesapp.data.local.dao.MoviesInfoDao
import com.silverbullet.moviesapp.data.local.dao.TrendingMoviesInfoDao
import com.silverbullet.moviesapp.data.local.entity.GenreEntity
import com.silverbullet.moviesapp.data.local.entity.MovieDetailsEntity
import com.silverbullet.moviesapp.data.local.entity.MovieInfoEntity
import com.silverbullet.moviesapp.data.local.entity.TrendingMovieInfoEntity

@Database(
    entities = [
        MovieDetailsEntity::class,
        MovieInfoEntity::class,
        TrendingMovieInfoEntity::class,
        GenreEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val moviesInfoDao: MoviesInfoDao
    abstract val movieDetailsDao: MovieDetailsDao
    abstract val genresDao: GenresDao
    abstract val trendingMovieInfoDao: TrendingMoviesInfoDao
}