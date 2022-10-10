package com.silverbullet.moviesapp.domain.repository

import com.silverbullet.moviesapp.domain.model.Genre
import com.silverbullet.moviesapp.domain.model.MovieDetails
import com.silverbullet.moviesapp.domain.model.MovieInfo
import com.silverbullet.moviesapp.domain.model.SearchResult
import com.silverbullet.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getTrendingMovies(): Flow<Resource<List<MovieInfo>>>

    fun getPopularMoves(page: Int): Flow<Resource<List<MovieInfo>>>

    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>

    fun getMoviesGenres(): Flow<Resource<List<Genre>>>

    suspend fun updateMovieDetails(movieDetails: MovieDetails)

    fun getFavoriteMoviesDetailsList(): Flow<List<MovieDetails>>

    fun search(
        query: String,
        page: Int
    ): Flow<Resource<SearchResult>>
}