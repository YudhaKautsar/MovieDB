package com.mypro.moviedb.ui.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypro.moviedb.data.model.Genre
import com.mypro.moviedb.data.repository.GenreRepository
import com.mypro.moviedb.util.Utils.errorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val repository: GenreRepository
) : ViewModel() {
    private val _genresLiveData = MutableLiveData<List<Genre>>()
    var genresLiveData: LiveData<List<Genre>> = _genresLiveData

    private val _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    fun getGenres(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getGenres(apiKey)
                _genresLiveData.value = response.genres
            } catch (e: Exception) {
                _errorLiveData.value = e.errorMessage()
            }
        }
    }
}