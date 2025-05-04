//package com.eshan.filmapp.ui.theme
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//data class Movie(
//    val title: String,
//    val year: String,
//    val rated: String,
//    val released: String,
//    val runtime: String,
//    val genre: String,
//    val director: String,
//    val writer: String,
//    val actors: String,
//    val plot: String
//)
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
//        // TODO: Will implement Room integration later
//    }
//}