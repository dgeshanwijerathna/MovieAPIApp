//package com.eshan.filmapp.network
//
//import android.util.Log
//import com.eshan.filmapp.model.Movie
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import org.json.JSONObject
//import java.net.HttpURLConnection
//import java.net.URL
//
//object OmdbApiService {
//
//    private const val API_KEY = "c7e54a67"
//
//    suspend fun fetchMovie(title: String): Movie? = withContext(Dispatchers.IO) {
//        val formattedTitle = title.trim().replace(" ", "+")
//        val urlString = "https://www.omdbapi.com/?t=$formattedTitle&apikey=$API_KEY"
//
//        try {
//            val url = URL(urlString)
//            val connection = url.openConnection() as HttpURLConnection
//            connection.requestMethod = "GET"
//            connection.connect()
//
//            val response = connection.inputStream.bufferedReader().use { it.readText() }
//
//            Log.d("OmdbApiService", "Raw response: $response") // ✅ Debug the actual response
//
//            val json = JSONObject(response)
//
//            if (json.optString("Response") == "True") {
//                Movie(
//                    title = json.optString("Title", "N/A"),
//                    year = json.optString("Year", "N/A"),
//                    rated = json.optString("Rated", "N/A"),
//                    released = json.optString("Released", "N/A"),
//                    runtime = json.optString("Runtime", "N/A"),
//                    genre = json.optString("Genre", "N/A"),
//                    director = json.optString("Director", "N/A"),
//                    writer = json.optString("Writer", "N/A"),
//                    actors = json.optString("Actors", "N/A"),
//                    plot = json.optString("Plot", "N/A")
//                )
//            } else {
//                Log.e("OmdbApiService", "Movie not found. Response: ${json.optString("Error")}")
//                null
//            }
//        } catch (e: Exception) {
//            Log.e("OmdbApiService", "Error fetching movie data", e)
//            null
//        }
//    }
//}
//
//

package com.eshan.filmapp.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.eshan.filmapp.model.Movie
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

object OmdbApiService {
    private const val API_KEY = "c7e54a67"
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    // Change: Callback-based approach instead of suspend function
    fun fetchMovie(title: String, callback: (Movie?) -> Unit) {
        executor.execute {
            val formattedTitle = title.trim().replace(" ", "+")
            val urlString = "https://www.omdbapi.com/?t=$formattedTitle&apikey=$API_KEY"

            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val response = connection.inputStream.bufferedReader().use { it.readText() }

                Log.d("OmdbApiService", "Raw response: $response")

                val json = JSONObject(response)
                val result = if (json.optString("Response") == "True") {
                    Movie(
                        title = json.optString("Title", "N/A"),
                        year = json.optString("Year", "N/A"),
                        rated = json.optString("Rated", "N/A"),
                        released = json.optString("Released", "N/A"),
                        runtime = json.optString("Runtime", "N/A"),
                        genre = json.optString("Genre", "N/A"),
                        director = json.optString("Director", "N/A"),
                        writer = json.optString("Writer", "N/A"),
                        actors = json.optString("Actors", "N/A"),
                        plot = json.optString("Plot", "N/A")
                    )
                } else {
                    Log.e("OmdbApiService", "Movie not found. Response: ${json.optString("Error")}")
                    null
                }

                // Post result back to main thread
                handler.post {
                    callback(result)
                }
            } catch (e: Exception) {
                Log.e("OmdbApiService", "Error fetching movie data", e)
                handler.post {
                    callback(null)
                }
            }
        }
    }
}
