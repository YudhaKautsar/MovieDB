package com.mypro.moviedb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mypro.moviedb.BuildConfig
import com.mypro.moviedb.data.model.Movie
import com.mypro.moviedb.data.model.Review
import com.mypro.moviedb.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class ReviewPagingSource(
    private val service: ApiService,
    private val id: Int
) : PagingSource<Int, Review>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getReviews(
                id = id,
                apiKey = BuildConfig.API_KEY,
                page = pageIndex
            )
            val reviews = response.results
            val nextKey =
                if (reviews.isEmpty()) {
                    null
                } else {
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = reviews,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}