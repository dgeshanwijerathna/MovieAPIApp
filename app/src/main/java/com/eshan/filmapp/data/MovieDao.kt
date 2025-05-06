package com.eshan.filmapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.eshan.filmapp.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieEntity>

    @Insert
    fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE LOWER(actors) LIKE '%' || LOWER(:actor) || '%'")
    fun searchMoviesByActor(actor: String): List<Movie>
}