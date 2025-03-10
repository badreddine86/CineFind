package com.example.cinefind.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinefind.R
import com.example.cinefind.core.utils.Response
import com.example.cinefind.data.models.Movie
import com.example.cinefind.data.models.MovieResponse
import com.example.cinefind.presentation.composables.CircularProgressCustom
import com.example.cinefind.presentation.composables.MovieItem
import com.example.cinefind.presentation.viewModels.MoviesViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import com.example.cinefind.presentation.composables.PaginationControls


@Composable
fun MovieListScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    var moviesList by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var currentPage by remember { mutableStateOf(1) }
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val errorMovList = stringResource(id = R.string.err_movies_list)

    LaunchedEffect(Unit) {
        viewModel.getMovies(currentPage)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = hostState) },
        topBar = {
            SearchBar(searchQuery) { newQuery ->
                searchQuery = newQuery
            }
        },
        bottomBar = {
            PaginationControls(
                currentPage = currentPage,
                onPageChange = { newPage ->
                    currentPage = newPage
                    viewModel.getMovies(newPage)
                }
            )
        }
    ) { innerPadding ->
        val filteredMovies = if (searchQuery.isBlank()) {
            moviesList
        } else {
            moviesList.filter { it.title.contains(searchQuery, ignoreCase = true) }
        }

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .safeDrawingPadding(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressCustom() // Show only the loader
                }
            }

            filteredMovies.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .safeDrawingPadding(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no_movies),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    items(filteredMovies) { movie ->
                        MovieItem(movie = movie)
                    }
                }
            }
        }

        DisplayMoviesState(
            flow = viewModel.movies,
            onSuccess = { movieResponse ->
                moviesList = movieResponse.results
                isLoading = false
            },
            onError = {
                isLoading = false
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
    LaunchedEffect(Unit) {
        flow.collect { response ->
            when (response) {
                is Response.Loading -> {
                    Log.i("Movies State -> ", "Loading")
                }

                is Response.Error -> {
                    Log.e("Movies State -> ", response.message)
                    onError()
                }

                is Response.Success -> {
                    Log.i("Movies State -> ", "Success")
                    onSuccess(response.data)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    TextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textStyle = TextStyle(fontSize = 18.sp),
        placeholder = { Text(text = "Search movies...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

