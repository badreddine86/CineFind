package com.example.cinefind.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinefind.R
import com.example.cinefind.core.utils.Response
import com.example.cinefind.data.models.Movie
import com.example.cinefind.data.models.MovieResponse
import com.example.cinefind.presentation.composables.CircularProgressCustom
import com.example.cinefind.presentation.composables.MovieItem
import com.example.cinefind.presentation.viewModels.MoviesViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@Composable
fun MovieListScreen(viewModel: MoviesViewModel = hiltViewModel()) {

    viewModel.getMovies(1)

    var moviesList by remember { mutableStateOf<List<Movie>>(emptyList()) }
    val hostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val errorMovList = stringResource(id = R.string.err_movies_list)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = hostState) },
    ){ innerPadding ->
        if (moviesList.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding).safeDrawingPadding(),
                contentAlignment = Alignment.Center
            ){
                Text(text = stringResource(id = R.string.no_movies), style =  MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp))
            }
        } else {
            LazyColumn {
                items(moviesList) { movie ->
                    MovieItem(movie = movie)
                }
            }
        }
        DisplayMoviesState(
            flow = viewModel.movies,
            onSuccess = { movieResponse ->
                moviesList = movieResponse.results
            },
            onError = {
                scope.launch { hostState.showSnackbar(errorMovList) }
            }
        )
    }


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