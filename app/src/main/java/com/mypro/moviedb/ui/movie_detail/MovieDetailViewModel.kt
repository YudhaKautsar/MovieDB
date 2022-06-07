package com.mypro.moviedb.ui.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypro.moviedb.data.model.Genre
import com.mypro.moviedb.data.model.Movie
import com.mypro.moviedb.data.model.MovieVideo
import com.mypro.moviedb.data.repository.GenreRepository
import com.mypro.moviedb.data.repository.MovieRepository
import com.mypro.moviedb.data.repository.ReviewRepository
import com.mypro.moviedb.util.Utils.errorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val reviewRepository: ReviewRepository,
) : ViewModel() {
    private val _movieLiveData = MutableLiveData<Movie>()
    var movieLiveData: LiveData<Movie> = _movieLiveData

    private val _movieVideosLiveData = MutableLiveData<List<MovieVideo>>()
    var movieVideosLiveData: LiveData<List<MovieVideo>> = _movieVideosLiveData

    private val _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    fun getMovie( id: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMovie(id, apiKey)
                _movieLiveData.value = response
            } catch (e: Exception) {
                _errorLiveData.value = e.errorMessage()
            }
        }
    }

    fun getMovieVideos( id: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMovieVideos(id, apiKey)
                _movieVideosLiveData.value = response.results
            } catch (e: Exception) {
                _errorLiveData.value = e.errorMessage()
            }
        }
    }


    fun getReviews(id : Int) = reviewRepository.getReviews(id)
}