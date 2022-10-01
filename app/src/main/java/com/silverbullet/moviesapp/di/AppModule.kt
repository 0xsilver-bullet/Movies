package com.silverbullet.moviesapp.di

import android.content.Context
import androidx.room.Room
import com.silverbullet.moviesapp.data.local.MoviesDatabase
import com.silverbullet.moviesapp.data.remote.TMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTMDBApi(): TMDBApi {
        return Retrofit
            .Builder()
            .baseUrl(TMDBApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room
            .databaseBuilder(
                context,
                MoviesDatabase::class.java,
                "MoviesDatabase.db"
            )
            .build()
    }
}