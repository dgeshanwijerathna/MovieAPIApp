//package com.eshan.filmapp.repository
//
//import com.eshan.filmapp.data.MovieDao
//import com.eshan.filmapp.data.MovieEntity
//import com.eshan.filmapp.model.Movie
//import org.json.JSONObject
//import java.net.HttpURLConnection
//import java.net.URL
//import java.net.URLEncoder
//
//class MovieRepository(private val movieDao: MovieDao) {
//    suspend fun insertAllMovies(movies: List<MovieEntity>) = movieDao.insertAll(movies)
//
//    suspend fun searchMoviesFromWeb(query: String): List<Movie> {
//        val encodedQuery = URLEncoder.encode(query, "UTF-8")
//        val apiKey = "c7e54a67"
//        val baseUrl = "https://www.omdbapi.com/"
//        val results = mutableListOf<Movie>()
//        var page = 1
//        var keepSearching = true
//
//        try {
//            while (keepSearching) {
//                val urlString = "$baseUrl?s=$encodedQuery&page=$page&apikey=$apiKey"
//                val url = URL(urlString)
//                val connection = url.openConnection() as HttpURLConnection
//                connection.requestMethod = "GET"
//
//                val response = connection.inputStream.bufferedReader().use { it.readText() }
//                val jsonObject = JSONObject(response)
//
//                if (jsonObject.optString("Response") == "True") {
//                    val searchArray = jsonObject.getJSONArray("Search")
//                    for (i in 0 until searchArray.length()) {
//                        val item = searchArray.getJSONObject(i)
//                        val title = item.getString("Title")
//                        if (title.contains(query, ignoreCase = true)) { // ✅ Substring match
//                            results.add(
//                                Movie(
//                                    title = title,
//                                    year = item.getString("Year"),
//                                    rated = "",
//                                    released = "",
//                                    runtime = "",
//                                    genre = "",
//                                    director = "",
//                                    writer = "",
//                                    actors = "",
//                                    plot = ""
//                                )
//                            )
//                        }
//                    }
//
//                    page++
//                    if (searchArray.length() < 10) keepSearching = false
//                } else {
//                    keepSearching = false
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return results
//    }
//
//
//}

package com.eshan.filmapp.repository

import android.os.Handler
import android.os.Looper
import com.eshan.filmapp.data.MovieDao
import com.eshan.filmapp.data.MovieEntity
import com.eshan.filmapp.model.Movie
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.concurrent.Executors

class MovieRepository(private val movieDao: MovieDao) {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    fun insertAllMovies(movies: List<MovieEntity>, onComplete: () -> Unit) {
        executor.execute {
            movieDao.insertAll(movies)
            handler.post {
                onComplete()
            }
        }
    }

    fun searchMoviesFromWeb(query: String, callback: (List<Movie>) -> Unit) {
        executor.execute {
            val results = mutableListOf<Movie>()

            try {
                val encodedQuery = URLEncoder.encode(query, "UTF-8")
                val apiKey = "c7e54a67"
                val baseUrl = "https://www.omdbapi.com/"
                var page = 1
                var keepSearching = true

                while (keepSearching) {
                    val urlString = "$baseUrl?s=$encodedQuery&page=$page&apikey=$apiKey"
                    val url = URL(urlString)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"

                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = JSONObject(response)

                    if (jsonObject.optString("Response") == "True") {
                        val searchArray = jsonObject.getJSONArray("Search")
                        for (i in 0 until searchArray.length()) {
                            val item = searchArray.getJSONObject(i)
                            val title = item.getString("Title")
                            if (title.contains(query, ignoreCase = true)) {
                                results.add(
                                    Movie(
                                        title = title,
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
                        }

                        page++
                        if (searchArray.length() < 10) keepSearching = false
                    } else {
                        keepSearching = false
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            handler.post {
                callback(results)
            }
        }
    }

    fun searchMoviesByActor(actor: String, callback: (List<Movie>) -> Unit) {
        executor.execute {
            val results = movieDao.searchMoviesByActor(actor)
            handler.post {
                callback(results)
            }
        }
    }
}