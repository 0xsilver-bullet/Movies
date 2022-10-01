package com.silverbullet.moviesapp.data.remote

import com.silverbullet.moviesapp.BuildConfig
import com.silverbullet.moviesapp.data.remote.dto.MovieDetailsDto
import com.silverbullet.moviesapp.data.remote.dto.PopularMoviesDto
import com.silverbullet.moviesapp.data.remote.dto.sub.Genre
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("/movie/popular")
    suspend fun fetchPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): PopularMoviesDto

    @GET("/movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailsDto

    @GET("/genre/movie/list")
    suspend fun fetchMovieGenres(
        @Query("api_key") apiKey: String = API_KEY
    )

    @GET("/trending/movie/day")
    suspend fun fetchTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): List<Genre>

    companion object {
        const val API_KEY = BuildConfig.TMDB_API_KEY
        const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/"

        fun buildImageUrl(path: String, size: Int): String {
            return IMAGE_URL + "w${size}/" + path
        }
    }
}