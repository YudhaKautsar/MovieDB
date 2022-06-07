package com.mypro.moviedb.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mypro.moviedb.data.model.Review
import com.mypro.moviedb.data.network.ApiService
import kotlinx.coroutines.flow.Flow

class ReviewDataSource(
    private val service: ApiService
) {

    fun getReviews(id: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewPagingSource(service = service, id = id)
            }
        ).flow
    }
}