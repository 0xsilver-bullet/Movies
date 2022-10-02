package com.silverbullet.moviesapp.data.repository

import com.silverbullet.moviesapp.data.local.entity.MovieDetailsEntity
import com.silverbullet.moviesapp.data.local.entity.MovieInfoEntity
import com.silverbullet.moviesapp.data.mappers.toMoveDetailsEntity
import com.silverbullet.moviesapp.data.mappers.toMovieInfoEntity
import com.silverbullet.moviesapp.data.remote.TMDBApi
import com.silverbullet.moviesapp.data.remote.dto.sub.Genre
import com.silverbullet.moviesapp.domain.repository.MoviesRepository
import com.silverbullet.moviesapp.utils.Resource
import java.io.IOException
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: TMDBApi
) : MoviesRepository {

    override suspend fun getTrendingMovies(): Resource<List<MovieInfoEntity>> {
        return try {
            Resource.Success(
                api
                    .fetchTrendingMovies()
                    .map { movieInfoDto ->
                        movieInfoDto.toMovieInfoEntity()
                    }
            )
        } catch (exception: Exception) {
            return Resource.Error(
                tmdbApiCallExceptionToString(exception)
            )
        }
    }

    override suspend fun getPopularMoves(page: Int): Resource<List<MovieInfoEntity>> {
        return try {
            Resource.Success(
                api
                    .fetchPopularMovies(page = page)
                    .results
                    .map { movieInfoDto ->
                        movieInfoDto.toMovieInfoEntity()
                    }
            )
        } catch (exception: Exception) {
            return Resource.Error(
                tmdbApiCallExceptionToString(exception)
            )
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetailsEntity> {
        return try {
            Resource.Success(
                api
                    .fetchMovieDetails(movieId)
                    .toMoveDetailsEntity()
            )
        } catch (exception: Exception) {
            return Resource.Error(
                tmdbApiCallExceptionToString(exception)
            )
        }
    }

    override suspend fun getMoviesGenres(): Resource<List<Genre>> {
        return try {
            Resource.Success(
                api
                    .fetchMovieGenres()
            )
        } catch (exception: Exception) {
            return Resource.Error(
                tmdbApiCallExceptionToString(exception)
            )
        }
    }

    companion object {
        fun tmdbApiCallExceptionToString(exception: Exception): String {
            return if (exception is IOException) {
                "No Internet connection"
            } else {
                exception.message ?: "Unexpected error occurred"
            }
        }
    }
}