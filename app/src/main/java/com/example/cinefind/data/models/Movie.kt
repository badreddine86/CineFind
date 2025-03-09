package com.example.cinefind.data.models

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int?,
    val title: String,
    @SerializedName("overview") val synopsis: String,
    @SerializedName("release_date") val date: String,
    @SerializedName("vote_average") val note: Double?,
    @SerializedName("backdrop_path") val image: String,
)