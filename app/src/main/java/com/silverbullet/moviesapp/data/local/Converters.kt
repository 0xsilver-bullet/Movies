package com.silverbullet.moviesapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.silverbullet.moviesapp.domain.model.Genre

class Converters {

    private val converter = Gson()

    @TypeConverter
    fun listOfGenresToJson(genresList: List<Genre>): String {
        return converter.toJson(genresList)
    }

    @TypeConverter
    fun jsonToListOfGenres(json: String): List<Genre> {
        return converter.fromJson(
            json,
            object : TypeToken<List<Genre>>() {}.type
        )
    }

    @TypeConverter
    fun listOfGenresIdsToJson(genresIdsList: List<Int>): String{
        return converter.toJson(genresIdsList)
    }

    @TypeConverter
    fun jsonToListOfGenresIds(json: String): List<Int>{
        return converter.fromJson(
            json,
            object :TypeToken<List<Int>>(){}.type
        )
    }
}