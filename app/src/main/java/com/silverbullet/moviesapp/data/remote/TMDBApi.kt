package com.silverbullet.moviesapp.data.remote

import com.silverbullet.moviesapp.BuildConfig
import com.silverbullet.moviesapp.data.remote.dto.*
import com.silverbullet.moviesapp.domain.model.Actor
import com.silverbullet.moviesapp.domain.model.MovieInfo
import com.silverbullet.moviesapp.domain.model.SearchResult
import com.silverbullet.moviesapp.domain.model.TrailerInfo
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException

interface TMDBApi {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): PopularMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailsDto

    @GET("genre/movie/list")
    suspend fun fetchMovieGenres(
        @Query("api_key") apiKey: String = API_KEY
    ): GenresResponse

    @GET("trending/movie/day")
    suspend fun fetchTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): TrendingMoviesResponse

    @GET("search/multi")
    suspend fun search(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int
    ): ResponseBody

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailer(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ResponseBody

    companion object {
        const val API_KEY = BuildConfig.TMDB_API_KEY
        const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/"

        fun buildImageUrl(path: String, size: Int): String {
            return IMAGE_URL + "w${size}/" + path
        }

        fun exceptionToErrorString(exception: Exception): String {
            return if (exception is IOException) {
                "No Internet connection"
            } else {
                exception.message ?: "Unexpected error occurred"
            }
        }

        fun parseSearchResponse(jsonResponse: String): SearchResult {
            val json = JSONObject(jsonResponse)
            val page = json.getInt("page")
            val totalPages = json.getInt("total_pages")
            val totalResults = json.getInt("total_results")
            val results = json.getJSONArray("results")
            var cursor = 0
            val moviesInfoList = mutableListOf<MovieInfo>()
            val actorsList = mutableListOf<Actor>()
            while (cursor < results.length()) {
                val jsonObj = results.getJSONObject(cursor)
                val type = jsonObj.getString("media_type")
                if (type == "movie") {
                    val movieInfo = parseMovieInfoObj(jsonObj)
                    moviesInfoList.add(movieInfo)
                } else if (type == "person") {
                    val actor = parseActorObj(jsonObj)
                    actorsList.add(actor)
                    // Extract movies the actor is known for and add it to movieInfoList
                    val knownForObjList = jsonObj.getJSONArray("known_for")
                    var knownForCursor = 0
                    while (knownForCursor < knownForObjList.length()) {
                        val movieInfoObj = knownForObjList.getJSONObject(knownForCursor)
                        val movieInfo = parseMovieInfoObj(movieInfoObj)
                        moviesInfoList.add(movieInfo)
                        knownForCursor++
                    }
                }
                cursor++
            }
            return SearchResult(
                page = page,
                totalPages = totalPages,
                totalResults = totalResults,
                movies = moviesInfoList,
                actors = actorsList
            )
        }

        /**
         * @return TrailerInfo object contains key to play from Youtube.
         */
        fun parseTrailerResponse(jsonResponse: String): TrailerInfo? {
            val jsonObj = JSONObject(jsonResponse)
            val results = jsonObj.getJSONArray("results")
            var resultsCursor = 0
            while (resultsCursor < results.length()) {
                val resultObj = results.getJSONObject(resultsCursor)
                val type = resultObj.getString("type")
                val site = resultObj.getString("site")
                if (type == "Trailer" && site == "YouTube") {
                    val id = resultObj.getString("id")
                    val key = resultObj.getString("key")
                    val name = resultObj.getString("name")
                    return TrailerInfo(name = name, id = id, key = key)
                }
                resultsCursor++
            }
            return null
        }

        private fun parseActorObj(obj: JSONObject): Actor {
            val name = obj.getString("name")
            val id = obj.getInt("id")
            val profilePath = obj.getString("profile_path")
            return Actor(name = name, id = id, profilePath = profilePath)
        }

        private fun parseMovieInfoObj(obj: JSONObject): MovieInfo {
            val title = obj.getString("title")
            val id = obj.getInt("id")
            val voteAverage = (obj.get("vote_average") as Number?)?.toDouble() ?: 0.0
            val backdropPath = obj.getString("backdrop_path")
            val posterPath = obj.getString("poster_path")
            val genreIdsObj = obj.getJSONArray("genre_ids")
            val genreIdsList = mutableListOf<Int>()
            var cursor = 0
            while (cursor < genreIdsObj.length()) {
                val genreId = genreIdsObj.getInt(cursor)
                genreIdsList.add(genreId)
                cursor++
            }
            val adult = obj.getBoolean("adult")
            return MovieInfo(
                id = id,
                title = title,
                adult = adult,
                voteAverage = voteAverage,
                backdropPath = backdropPath,
                posterPath = posterPath,
                genreIds = genreIdsList
            )
        }
    }
}