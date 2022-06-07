package com.mypro.moviedb.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.mypro.moviedb.data.model.Movie
import com.mypro.moviedb.data.model.Review
import com.mypro.moviedb.data.network.ApiService
import com.mypro.moviedb.data.paging.MovieDataSource
import com.mypro.moviedb.data.paging.ReviewDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReviewRepository(
    private val dataSource: ReviewDataSource,
    private val service: ApiService
) {

    fun getReviews(id: Int): Flow<PagingData<Review>> {
        return dataSource.getReviews(id)
            .map { pagingData ->
                pagingData.map { review ->
                    Review(
                        id = review.id,
                        author = review.author,
                        content = review.content
                    )
                }
            }
    }

}