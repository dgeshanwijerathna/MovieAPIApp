//package com.eshan.filmapp.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.eshan.filmapp.model.Movie
//import com.eshan.filmapp.network.OmdbApiService
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class SearchMovieViewModel : ViewModel() {
//
//    private val _movie = MutableStateFlow<Movie?>(null)
//    val movie = _movie.asStateFlow()
//
//    fun retrieveMovie(movieTitle: String) {
//        viewModelScope.launch {
//            val result = OmdbApiService.fetchMovie(movieTitle)
//            _movie.value = result
//        }
//    }
//
//    fun saveMovieToDatabase() {
//        // Room database logic will go here in next steps
//    }
//}

package com.eshan.filmapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshan.filmapp.model.Movie // Ensure this is correctly imported
import com.eshan.filmapp.network.OmdbApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchMovieViewModel : ViewModel() {

    // StateFlow to hold the movie data
    private val _movie = MutableStateFlow<Movie?>(null)
   val movie = _movie.asStateFlow()

    private val _searchResult = MutableStateFlow<Movie?>(null)
    val searchResult = _searchResult.asStateFlow()


    // Function to fetch movie from OMDb API
    fun retrieveMovie(title: String) {
        viewModelScope.launch {
            val movie = OmdbApiService.fetchMovie(title)
            if (movie != null) {
                _searchResult.value = movie
            } else {
                Log.d("SearchMovieViewModel", "No movie found for title: $title")
            }
        }
    }


    // Function to save movie to database (to be implemented later)
    fun saveMovieToDatabase() {
        // TODO: Room database logic will go here in next steps
    }
}
