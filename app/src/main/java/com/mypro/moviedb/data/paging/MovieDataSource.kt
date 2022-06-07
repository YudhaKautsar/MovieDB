package com.mypro.moviedb.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mypro.moviedb.data.model.Movie
import com.mypro.moviedb.data.network.ApiService
import kotlinx.coroutines.flow.Flow


const val NETWORK_PAGE_SIZE = 60

class MovieDataSource(
    private val service: ApiService
) {

    fun getMovies(genre: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(service = service, genre = genre)
            }
        ).flow
    }
}