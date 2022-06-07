package com.mypro.moviedb.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.mypro.moviedb.data.model.Movie
import com.mypro.moviedb.data.network.ApiService
import com.mypro.moviedb.data.paging.MovieDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val dataSource: MovieDataSource,
    private val service: ApiService
) {

    fun getMovies(genre: Int): Flow<PagingData<Movie>> {
        return dataSource.getMovies(genre)
            .map { pagingData ->
                pagingData.map { remoteMovie ->
                    Movie(
                        id = remoteMovie.id,
                        title = remoteMovie.title,
                        image = remoteMovie.image,
                        releaseDate = remoteMovie.releaseDate,
                        rating = remoteMovie.rating,
                        backdrop = remoteMovie.backdrop,
                        status = remoteMovie.status
                    )
                }
            }
    }

    suspend fun getMovie( id: Int, apiKey: String) = service.getMovie(id, apiKey)

    suspend fun getMovieVideos( id: Int, apiKey: String) = service.getMovieVideos(id, apiKey)
}