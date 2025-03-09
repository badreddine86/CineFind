package com.example.cinefind.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinefind.core.utils.Response
import com.example.cinefind.data.models.Movie
import com.example.cinefind.data.models.MovieResponse
import com.example.cinefind.domain.usecases.GetMoviesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<Response<MovieResponse>>(Response.Loading)
    val movies: StateFlow<Response<MovieResponse>> = _movies

    init {
        // Optionally, load movies when ViewModel is initialized
        // getMovies(1)
    }

    fun getMovies(page: Int) {
        viewModelScope.launch {
            // Fetch movies and map to Response<MovieResponse>
            getMoviesListUseCase.invoke(page).collect { response ->
                _movies.value = response
            }
        }
    }
}
