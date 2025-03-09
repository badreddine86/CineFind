package com.example.cinefind.domain.usecases

import com.example.cinefind.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMoviesListUseCase @Inject constructor(private val moviesRepository: MoviesRepository)  {
    suspend fun invoke(page: Int) = moviesRepository.getMovies(page)
}