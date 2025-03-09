package com.example.cinefind.di

import com.example.cinefind.data.repositoriesImpl.MoviesRepositoryImpl
import com.example.cinefind.data.sources.ApiService
import com.example.cinefind.data.sources.RetrofitClient
import com.example.cinefind.domain.repositories.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(apiService: ApiService): MoviesRepository {
        return MoviesRepositoryImpl(apiService)

    }
}