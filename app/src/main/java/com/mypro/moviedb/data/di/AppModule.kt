package com.mypro.moviedb.data.di

import android.content.Context
import com.mypro.moviedb.data.network.ApiService
import com.mypro.moviedb.data.paging.MovieDataSource
import com.mypro.moviedb.data.paging.ReviewDataSource
import com.mypro.moviedb.data.repository.GenreRepository
import com.mypro.moviedb.data.repository.MovieRepository
import com.mypro.moviedb.data.repository.ReviewRepository
import com.mypro.moviedb.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesGenreRepository(apiService: ApiService): GenreRepository =
        GenreRepository(apiService)

    @Singleton
    @Provides
    fun providesMovieRepository(apiService: ApiService,movieDataSource: MovieDataSource): MovieRepository =
        MovieRepository(movieDataSource, apiService)

    @Singleton
    @Provides
    fun providesReviewRepository(apiService: ApiService, reviewDataSource: ReviewDataSource): ReviewRepository =
        ReviewRepository(reviewDataSource, apiService)

    @Singleton
    @Provides
    fun providesMovieDataSource(apiService: ApiService): MovieDataSource =
        MovieDataSource(apiService)

    @Singleton
    @Provides
    fun providesReviewDataSource(apiService: ApiService): ReviewDataSource =
        ReviewDataSource(apiService)


}