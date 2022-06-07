package com.mypro.moviedb.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val image: String? = null,
    @SerializedName("backdrop_path")
    val backdrop: String? = null,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val rating: Double,
    val status: String? = null
) {
    fun getReleaseYear() = releaseDate.take(4)
}

