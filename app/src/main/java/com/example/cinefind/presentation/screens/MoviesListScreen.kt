package com.example.cinefind.presentation.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinefind.core.utils.Response
import com.example.cinefind.data.models.Movie
import com.example.cinefind.data.models.MovieResponse
import com.example.cinefind.presentation.composables.CircularProgressCustom
import com.example.cinefind.presentation.composables.MovieItem
import com.example.cinefind.presentation.viewModels.MoviesViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun MovieListScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    // Appel pour récupérer les films
    viewModel.getMovies(1)

    // Observer les films dans le ViewModel
    val moviesResponse = viewModel.movies.collectAsState()

    var moviesList by remember { mutableStateOf<List<Movie>>(emptyList()) }

    Log.i("MovieListScreen", "moviesList: $moviesList")

    LazyColumn {
        items(moviesList) { movie ->
            MovieItem(movie = movie)
        }
    }

    DisplayMoviesState(
        flow = viewModel.movies,
        onSuccess = { movieResponse ->
            // Get the list of movies from the response and update the list
            moviesList = movieResponse.results
        },
        onError = {
            // Handle error
        }
    )
}

@Composable
fun DisplayMoviesState(
    flow: StateFlow<Response<MovieResponse>>,
    onSuccess: (MovieResponse) -> Unit,
    onError: () -> Unit
) {
    val isLoading = remember { mutableStateOf(false) }
    if (isLoading.value) CircularProgressCustom()
    LaunchedEffect(Unit) {
        flow.collect {
            when (it) {
                is Response.Loading -> {
                    Log.i("Add Purchase state -> ", "Loading")
                    isLoading.value = true
                }

                is Response.Error -> {
                    Log.e("Add Purchase state -> ", it.message)
                    isLoading.value = false
                    onError()
                }

                is Response.Success -> {
                    Log.i("Add Purchase state -> ", "Success")
                    isLoading.value = false
                    onSuccess(it.data)
                }
            }
        }
    }
}