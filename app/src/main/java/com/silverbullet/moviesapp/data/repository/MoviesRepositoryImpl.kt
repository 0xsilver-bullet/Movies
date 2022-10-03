package com.silverbullet.moviesapp.data.repository

import com.silverbullet.moviesapp.data.local.MoviesDatabase
import com.silverbullet.moviesapp.data.mappers.*
import com.silverbullet.moviesapp.data.remote.TMDBApi
import com.silverbullet.moviesapp.domain.model.Genre
import com.silverbullet.moviesapp.domain.model.MovieDetails
import com.silverbullet.moviesapp.domain.model.MovieInfo
import com.silverbullet.moviesapp.domain.repository.MoviesRepository
import com.silverbullet.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: TMDBApi,
    database: MoviesDatabase
) : MoviesRepository {

    private val trendingMoviesInfoDao = database.trendingMovieInfoDao
    private val moviesInfoDao = database.moviesInfoDao
    private val movieDetailsDao = database.movieDetailsDao
    private val genreDao = database.genresDao

    override suspend fun getTrendingMovies(): Flow<Resource<List<MovieInfo>>> {
        return flow {
            emit(Resource.Loading())
            val trendingMoviesEntities = trendingMoviesInfoDao.getMovies()
            if (trendingMoviesEntities.isNotEmpty()) {
                emit(
                    Resource.Success(
                        trendingMoviesEntities.map { it.toMovieInfo() },
                        isLoading = true
                    )
                )
            }
            try {
                val trendingMovies = api.fetchTrendingMovies()
                trendingMoviesInfoDao.clear()
                trendingMoviesInfoDao.insertAll(trendingMovies.map { it.toTrendingMovieEntity() })
                emit(
                    Resource.Success(
                        trendingMovies.map { it.toMovieInfoEntity().toMovieInfo() }
                    )
                )
            } catch (exception: Exception) {
                emit(Resource.Error(TMDBApi.exceptionToErrorString(exception)))
            }
        }
    }

    override suspend fun getPopularMoves(page: Int): Flow<Resource<List<MovieInfo>>> {
        return flow {
            emit(Resource.Loading())
            val popularMoviesEntities = moviesInfoDao.getMoviesInfo()
            if (popularMoviesEntities.isNotEmpty()) {
                emit(
                    Resource.Success(
                        popularMoviesEntities.map { it.toMovieInfo() },
                        isLoading = true
                    )
                )
            }
            try {
                val newPopularMoviesResponse = api.fetchPopularMovies(page = page)
                if (page == 1) {
                    // Then user has just launched the app and we want fresh content in the database
                    // and fresh content on the screen, so we clear the database and cache new data.
                    moviesInfoDao.clear()
                }
                moviesInfoDao.insertAll(newPopularMoviesResponse.results.map { it.toMovieInfoEntity() })
                emit(
                    Resource.Success(
                        moviesInfoDao.getMoviesInfo().map { it.toMovieInfo() }
                    )
                )
            } catch (e: Exception) {
                emit(
                    Resource.Error(TMDBApi.exceptionToErrorString(e))
                )
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> {
        return flow {
            emit(Resource.Loading())
            val movieDetails = movieDetailsDao.getMovieDetail(movieId)
            if (movieDetails != null) {
                emit(Resource.Success(movieDetails.toMovieDetails()))
            } else {
                // This movie details is not cached and needs to be fetched from api.
                try {
                    val movieDetailsResponse = api.fetchMovieDetails(movieId = movieId)
                    val movieDetailsEntity = movieDetailsResponse.toMoveDetailsEntity()
                    movieDetailsDao.upsert(movieDetailsEntity)
                    emit(
                        Resource.Success(movieDetailsEntity.toMovieDetails())
                    )
                } catch (e: Exception) {
                    emit(Resource.Error(TMDBApi.exceptionToErrorString(e)))
                }
            }
        }
    }

    override suspend fun getMoviesGenres(): Flow<Resource<List<Genre>>> {
        return flow {
            emit(Resource.Loading())
            val genreEntities = genreDao.getGenres()
            if (genreEntities.isNotEmpty()) {
                emit(
                    Resource.Success(genreEntities.map { it.toGenre() })
                )
                return@flow
            }
            val entitiesResponse = api.fetchMovieGenres()
            genreDao.insertAll(entitiesResponse.map { it.toGenreEntity() })
            emit(
                Resource.Success(
                    genreDao.getGenres().map { it.toGenre() }
                )
            )
        }
    }
}