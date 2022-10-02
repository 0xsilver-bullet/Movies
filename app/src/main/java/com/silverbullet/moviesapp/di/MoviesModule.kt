package com.silverbullet.moviesapp.di

import com.silverbullet.moviesapp.data.repository.MoviesRepositoryImpl
import com.silverbullet.moviesapp.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepositoryImpl(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository
}