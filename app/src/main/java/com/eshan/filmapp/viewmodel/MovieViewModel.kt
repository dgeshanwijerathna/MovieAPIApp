package com.eshan.filmapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eshan.filmapp.data.MovieDatabase
import com.eshan.filmapp.data.MovieEntity
import com.eshan.filmapp.model.Movie
import com.eshan.filmapp.repository.MovieRepository
import java.util.concurrent.Executors

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository
    private val executor = Executors.newSingleThreadExecutor()

    private val _searchResults = MutableLiveData<List<Movie>>(emptyList())
    val searchResults: LiveData<List<Movie>> = _searchResults

    private val _webSearchResults = MutableLiveData<List<Movie>>(emptyList())
    val webSearchResults: LiveData<List<Movie>> = _webSearchResults

    init {
        val movieDao = MovieDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(movieDao)
    }

    fun searchMoviesByActor(query: String) {
        repository.searchMoviesByActor(query) { results ->
            _searchResults.value = results
        }
    }

    fun searchMoviesByTitleFromWeb(query: String) {
        repository.searchMoviesFromWeb(query) { results ->
            _webSearchResults.value = results
        }
    }

    fun insertHardcodedMovies() {
        val movies = listOf(
            MovieEntity(title = "The Shawshank Redemption", year = "1994", rated = "R", released = "14 Oct 1994",
                runtime = "142 min", genre = "Drama", director = "Frank Darabont",
                writer = "Stephen King, Frank Darabont", actors = "Tim Robbins, Morgan Freeman, Bob Gunton",
                plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."),
            MovieEntity(title = "Batman: The Dark Knight Returns, Part 1", year = "2012", rated = "PG-13", released = "25 Sep 2012",
                runtime = "76 min", genre = "Animation, Action, Crime, Drama, Thriller", director = "Jay Oliva",
                writer = "Bob Kane, Frank Miller, Klaus Janson, Bob Goodman", actors = "Peter Weller, Ariel Winter, David Selby, Wade Williams",
                plot = "Batman has not been seen for ten years. A new breed of criminal ravages Gotham City, forcing 55-year-old Bruce Wayne back into the cape and cowl."),
            MovieEntity(title = "The Lord of the Rings: The Return of the King", year = "2003", rated = "PG-13", released = "17 Dec 2003",
                runtime = "201 min", genre = "Action, Adventure, Drama", director = "Peter Jackson",
                writer = "J.R.R. Tolkien, Fran Walsh, Philippa Boyens", actors = "Elijah Wood, Viggo Mortensen, Ian McKellen",
                plot = "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam."),
            MovieEntity(title = "Inception", year = "2010", rated = "PG-13", released = "16 Jul 2010",
                runtime = "148 min", genre = "Action, Adventure, Sci-Fi", director = "Christopher Nolan",
                writer = "Christopher Nolan", actors = "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page",
                plot = "A thief who steals corporate secrets through dream-sharing technology is tasked with planting an idea."),
            MovieEntity(title = "The Matrix", year = "1999", rated = "R", released = "31 Mar 1999",
                runtime = "136 min", genre = "Action, Sci-Fi", director = "Lana Wachowski, Lilly Wachowski",
                writer = "Lilly Wachowski, Lana Wachowski", actors = "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
                plot = "Neo discovers the life he knows is an elaborate deception of an evil cyber-intelligence.")
        )
        repository.insertAllMovies(movies) {
        }

    }
}
