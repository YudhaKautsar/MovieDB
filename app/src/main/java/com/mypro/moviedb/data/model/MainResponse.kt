package com.mypro.moviedb.data.model

data class MainResponse<T>(
    val results: List<T>
)