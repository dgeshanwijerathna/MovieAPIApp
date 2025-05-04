// data/MovieDao.kt
package com.eshan.filmapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//@Dao
//interface MovieDao {
//    @Insert
//    suspend fun insertAll(movies: List<MovieEntity>)
//
//    @Query("SELECT * FROM movies")
//    suspend fun getAllMovies(): List<MovieEntity>
//}


@Dao
interface MovieDao {

    // This will return a Flow of List<MovieEntity> to observe changes in the database
    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    // This will insert a list of movies
    @Insert
    suspend fun insertAll(movies: List<MovieEntity>)
}
