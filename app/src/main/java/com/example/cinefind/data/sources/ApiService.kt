package com.example.cinefind.data.sources

import com.example.cinefind.data.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = RetrofitClient.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): Call<MovieResponse>
}