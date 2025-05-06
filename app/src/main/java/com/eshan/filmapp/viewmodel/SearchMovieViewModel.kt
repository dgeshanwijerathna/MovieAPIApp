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
            }

        }
    }
}
