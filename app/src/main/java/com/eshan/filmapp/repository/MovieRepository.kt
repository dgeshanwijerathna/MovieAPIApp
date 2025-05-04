// repository/MovieRepository.kt
package com.eshan.filmapp.repository

import com.eshan.filmapp.data.MovieDao
import com.eshan.filmapp.data.MovieEntity

class MovieRepository(private val movieDao: MovieDao) {
    suspend fun insertAllMovies(movies: List<MovieEntity>) = movieDao.insertAll(movies)
}
