package com.silverbullet.moviesapp.domain.repository

import com.silverbullet.moviesapp.data.local.entity.MovieDetailsEntity
import com.silverbullet.moviesapp.data.local.entity.MovieInfoEntity
import com.silverbullet.moviesapp.data.remote.dto.sub.Genre
import com.silverbullet.moviesapp.utils.Resource

interface MoviesRepository {

    suspend fun getTrendingMovies(): Resource<List<MovieInfoEntity>>

    suspend fun getPopularMoves(page: Int): Resource<List<MovieInfoEntity>>

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetailsEntity>

    suspend fun getMoviesGenres(): Resource<List<Genre>>
}