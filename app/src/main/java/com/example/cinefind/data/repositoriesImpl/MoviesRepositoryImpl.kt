package com.example.cinefind.data.repositoriesImpl

import com.example.cinefind.core.utils.Response
import retrofit2.Response as RetrofitResponse
import com.example.cinefind.data.models.Movie
import com.example.cinefind.data.models.MovieResponse
import com.example.cinefind.data.sources.ApiService
import com.example.cinefind.domain.repositories.MoviesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val apiService: ApiService)  : MoviesRepository {

    override suspend fun getMovies(page: Int): Flow<Response<MovieResponse>> = callbackFlow {

        val call = apiService.getPopularMovies(page = page)

        val callback = object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: RetrofitResponse<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        trySend(Response.Success(movieResponse))
                    } ?: run {
                        trySend(Response.Error("Empty response body"))
                    }
                } else {
                    trySend(Response.Error("Error: ${response.code()}"))
                }
                close()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                trySend(Response.Error("Failure: ${t.localizedMessage}"))
                close()
            }
        }
        call.enqueue(callback)

        awaitClose { call.cancel() }
    }




    override suspend fun getMovieDetails(): Movie {
        TODO("Not yet implemented")
    }
}