package com.mypro.moviedb.ui.movie

import androidx.lifecycle.ViewModel
import com.mypro.moviedb.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    fun getMovies(genre : Int) = repository.getMovies(genre)
}