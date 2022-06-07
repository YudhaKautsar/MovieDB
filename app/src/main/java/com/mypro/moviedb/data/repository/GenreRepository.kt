package com.mypro.moviedb.data.repository

import com.mypro.moviedb.data.network.ApiService

class GenreRepository(private val apiService: ApiService) {
    suspend fun getGenres(apiKey: String) = apiService.getGenres(apiKey)
}