package com.eshan.filmapp.network

import android.util.Log
import com.eshan.filmapp.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

//object OmdbApiService {
//
//    private const val API_KEY = "c7e54a67" // Replace with your actual key
//
//    fun fetchMovie(title: String): Movie? {
//        val formattedTitle = title.trim().replace(" ", "+")
//        val urlString = "https://www.omdbapi.com/?t=$formattedTitle&apikey=$API_KEY"
//
//        val url = URL(urlString)
//        val connection = url.openConnection() as HttpURLConnection
//
//        return try {
//            connection.requestMethod = "GET"
//            connection.connect()
//
//            val response = connection.inputStream.bufferedReader().use { it.readText() }
//            val json = JSONObject(response)
//
//            if (json.getString("Response") == "True") {
//                Movie(
//                    title = json.getString("Title"),
//                    year = json.getString("Year"),
//                    rated = json.getString("Rated"),
//                    released = json.getString("Released"),
//                    runtime = json.getString("Runtime"),
//                    genre = json.getString("Genre"),
//                    director = json.getString("Director"),
//                    writer = json.getString("Writer"),
//                    actors = json.getString("Actors"),
//                    plot = json.getString("Plot")
//                )
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
//                null
//            }
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        } finally {
//            connection.disconnect()
//        }
//    }
//}

object OmdbApiService {

    private const val API_KEY = "c7e54a67"

    suspend fun fetchMovie(title: String): Movie? = withContext(Dispatchers.IO) {
        val formattedTitle = title.trim().replace(" ", "+")
        val urlString = "https://www.omdbapi.com/?t=$formattedTitle&apikey=$API_KEY"

        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val response = connection.inputStream.bufferedReader().use { it.readText() }

            Log.d("OmdbApiService", "Raw response: $response") // ✅ Debug the actual response

            val json = JSONObject(response)

            if (json.optString("Response") == "True") {
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
        } catch (e: Exception) {
            Log.e("OmdbApiService", "Error fetching movie data", e)
            null
        }
    }
}