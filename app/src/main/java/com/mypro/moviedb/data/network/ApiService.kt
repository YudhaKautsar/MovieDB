package com.mypro.moviedb.data.network

import com.mypro.moviedb.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String
    ): GenreResponse

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genre: Int,
        @Query("page") page: Int
    ): MainResponse<Movie>

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Movie

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): MainResponse<MovieVideo>

    @GET("movie/{id}/reviews")
    suspend fun getReviews(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MainResponse<Review>
}