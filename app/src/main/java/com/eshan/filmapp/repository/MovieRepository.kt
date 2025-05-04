// repository/MovieRepository.kt
package com.eshan.filmapp.repository

import com.eshan.filmapp.data.MovieDao
import com.eshan.filmapp.data.MovieEntity
import com.eshan.filmapp.model.Movie
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MovieRepository(private val movieDao: MovieDao) {
    suspend fun insertAllMovies(movies: List<MovieEntity>) = movieDao.insertAll(movies)

    suspend fun searchMoviesFromWeb(query: String): List<Movie> {
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val apiKey = "c7e54a67" // Replace with your actual OMDb API key
        val urlString = "https://www.omdbapi.com/?s=$encodedQuery&apikey=$apiKey"

        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(response)

            if (jsonObject.getString("Response") == "True") {
                val searchArray = jsonObject.getJSONArray("Search")
                val results = mutableListOf<Movie>()

                for (i in 0 until searchArray.length()) {
                    val item = searchArray.getJSONObject(i)
                    results.add(
                        Movie(
                            title = item.getString("Title"),
                            year = item.getString("Year"),
                            rated = "",
                            released = "",
                            runtime = "",
                            genre = "",
                            director = "",
                            writer = "",
                            actors = "",
                            plot = ""
                        )
                    )
                }
                results
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

}
