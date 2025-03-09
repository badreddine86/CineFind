package com.example.cinefind.data.sources

import io.github.cdimascio.dotenv.Dotenv
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val dotenv: Dotenv = Dotenv.configure().directory("/assets").filename("env").load();

    private val BASE_URL = dotenv.get("BASE_URL")
    val API_KEY: String = dotenv.get("API_KEY")

    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}