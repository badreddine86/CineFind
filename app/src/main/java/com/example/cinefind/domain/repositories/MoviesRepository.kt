package com.example.cinefind.domain.repositories

import com.example.cinefind.core.utils.Response
import com.example.cinefind.data.models.Movie
import com.example.cinefind.data.models.MovieResponse
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

    suspend fun getMovies(page: Int): Flow<Response<MovieResponse>>

}