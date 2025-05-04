////package com.eshan.filmapp.viewmodel
////
////import androidx.lifecycle.ViewModel
////import androidx.lifecycle.viewModelScope
////import com.eshan.filmapp.model.Movie
////import com.eshan.filmapp.network.OmdbApiService
////import kotlinx.coroutines.flow.MutableStateFlow
////import kotlinx.coroutines.flow.asStateFlow
////import kotlinx.coroutines.launch
////
////class SearchMovieViewModel : ViewModel() {
////
////    private val _movie = MutableStateFlow<Movie?>(null)
////    val movie = _movie.asStateFlow()
////
////    fun retrieveMovie(movieTitle: String) {
////        viewModelScope.launch {
////            val result = OmdbApiService.fetchMovie(movieTitle)
////            _movie.value = result
////        }
////    }
////
////    fun saveMovieToDatabase() {
////        // Room database logic will go here in next steps
////    }
////}
//
////package com.eshan.filmapp.viewmodel
////
////import android.util.Log
////import androidx.lifecycle.LiveData
////import androidx.lifecycle.MutableLiveData
////import androidx.lifecycle.ViewModel
////import androidx.lifecycle.viewModelScope
////import com.eshan.filmapp.model.Movie // Ensure this is correctly imported
////import com.eshan.filmapp.network.OmdbApiService
////import kotlinx.coroutines.flow.MutableStateFlow
////import kotlinx.coroutines.flow.asStateFlow
////import kotlinx.coroutines.launch
////
////class SearchMovieViewModel : ViewModel() {
////
////    // StateFlow to hold the movie data
////    private val _movie = MutableStateFlow<Movie?>(null)
////   val movie = _movie.asStateFlow()
////
////    private val _searchResult = MutableStateFlow<Movie?>(null)
////    val searchResult = _searchResult.asStateFlow()
////
////
////    // Function to fetch movie from OMDb API
////    fun retrieveMovie(title: String) {
////        viewModelScope.launch {
////            val movie = OmdbApiService.fetchMovie(title)
////            if (movie != null) {
////                _searchResult.value = movie
////            } else {
////                Log.d("SearchMovieViewModel", "No movie found for title: $title")
////            }
////        }
////    }
////
////
////    // Function to save movie to database (to be implemented later)
////    fun saveMovieToDatabase() {
////        // TODO: Room database logic will go here in next steps
////    }
////}
//
//package com.eshan.filmapp.viewmodel
//
//import android.app.Application
//import android.util.Log
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import com.eshan.filmapp.data.MovieDatabase
//import com.eshan.filmapp.data.MovieEntity
//import com.eshan.filmapp.model.Movie
//import com.eshan.filmapp.network.OmdbApiService
//import com.eshan.filmapp.repository.MovieRepository
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class SearchMovieViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val _searchResult = MutableStateFlow<Movie?>(null)
//    val searchResult = _searchResult.asStateFlow()
//
//    private val dao = MovieDatabase.getDatabase(application).movieDao()
//    private val repository = MovieRepository(dao)
//
//    fun retrieveMovie(title: String) {
//        viewModelScope.launch {
//            val movie = OmdbApiService.fetchMovie(title)
//            if (movie != null) {
//                _searchResult.value = movie
//            } else {
//                Log.d("SearchMovieViewModel", "No movie found for title: $title")
//            }
//        }
//    }
//
//    fun saveMovieToDatabase() {
//        val movie = _searchResult.value
//        if (movie != null) {
//            val movieEntity = MovieEntity(
//                title = movie.title,
//                year = movie.year,
//                rated = movie.rated ?: "N/A",
//                released = movie.released ?: "N/A",
//                runtime = movie.runtime ?: "N/A",
//                genre = movie.genre ?: "N/A",
//                director = movie.director ?: "N/A",
//                writer = movie.writer ?: "N/A",
//                actors = movie.actors ?: "N/A",
//                plot = movie.plot ?: "N/A"
//            )
//
//            viewModelScope.launch(Dispatchers.IO) {
//                repository.insertAllMovies(listOf(movieEntity))
//                Log.d("SearchMovieViewModel", "Movie saved to DB: ${movie.title}")
//            }
//        }
//    }
//}

package com.eshan.filmapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eshan.filmapp.data.MovieDatabase
import com.eshan.filmapp.data.MovieEntity
import com.eshan.filmapp.model.Movie
import com.eshan.filmapp.network.OmdbApiService
import com.eshan.filmapp.repository.MovieRepository

class SearchMovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository

    private val _searchResult = MutableLiveData<Movie?>(null)
    val searchResult: LiveData<Movie?> = _searchResult

    private var currentMovie: Movie? = null

    init {
        val movieDao = MovieDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(movieDao)
    }

    fun retrieveMovie(title: String) {
        OmdbApiService.fetchMovie(title) { movie ->
            currentMovie = movie
            _searchResult.value = movie
        }
    }

    fun saveMovieToDatabase() {
        currentMovie?.let { movie ->
            val movieEntity = MovieEntity(
                title = movie.title,
                year = movie.year,
                rated = movie.rated ?: "N/A",
                released = movie.released ?: "N/A",
                runtime = movie.runtime ?: "N/A",
                genre = movie.genre ?: "N/A",
                director = movie.director ?: "N/A",
                writer = movie.writer ?: "N/A",
                actors = movie.actors ?: "N/A",
                plot = movie.plot ?: "N/A"
            )

            repository.insertAllMovies(listOf(movieEntity)) {
                // Handle completion if needed
            }
        }
    }
}
