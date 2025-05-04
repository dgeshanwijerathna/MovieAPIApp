//package com.eshan.filmapp.ui.theme
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.eshan.filmapp.viewmodel.SearchMovieViewModel
//import androidx.compose.runtime.collectAsState
//
//
//
//@Composable
//fun SearchMovieScreen(viewModel: SearchMovieViewModel = viewModel()) {
//    var movieTitle by remember { mutableStateOf("") }
//
//    val movieResult by viewModel.searchResult.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        OutlinedTextField(
//            value = movieTitle,
//            onValueChange = { movieTitle = it },
//            label = { Text("Enter Movie Title") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            Button(onClick = { viewModel.retrieveMovie(movieTitle) }) {
//                Text("Retrieve Movie")
//            }
//
//            Button(onClick = { viewModel.saveMovieToDatabase() }) {
//                Text("Save Movie to Database")
//            }
//        }
//
//        movieResult?.let { movie ->
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(text = "Title: ${movie.title}")
//            Text(text = "Year: ${movie.year}")
//            Text(text = "Rated: ${movie.rated}")
//            Text(text = "Released: ${movie.released}")
//            Text(text = "Runtime: ${movie.runtime}")
//            Text(text = "Genre: ${movie.genre}")
//            Text(text = "Director: ${movie.director}")
//            Text(text = "Writer: ${movie.writer}")
//            Text(text = "Actors: ${movie.actors}")
//            Text(text = "Plot: ${movie.plot}")
//        }
//
//    }
//}
package com.eshan.filmapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eshan.filmapp.viewmodel.SearchMovieViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.eshan.filmapp.util.observeAsState

@Composable
fun SearchMovieScreen(viewModel: SearchMovieViewModel = viewModel()) {
    var movieTitle by remember { mutableStateOf("") }

    // Change: Using observeAsState instead of collectAsState
    val movieResult by viewModel.searchResult.observeAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = movieTitle,
            onValueChange = { movieTitle = it },
            label = { Text("Enter Movie Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.retrieveMovie(movieTitle) }) {
                Text("Retrieve Movie")
            }

            Button(onClick = { viewModel.saveMovieToDatabase() }) {
                Text("Save Movie to Database")
            }
        }

        movieResult?.let { movie ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Title: ${movie.title}")
            Text(text = "Year: ${movie.year}")
            Text(text = "Rated: ${movie.rated ?: "N/A"}")
            Text(text = "Released: ${movie.released ?: "N/A"}")
            Text(text = "Runtime: ${movie.runtime ?: "N/A"}")
            Text(text = "Genre: ${movie.genre ?: "N/A"}")
            Text(text = "Director: ${movie.director ?: "N/A"}")
            Text(text = "Writer: ${movie.writer ?: "N/A"}")
            Text(text = "Actors: ${movie.actors ?: "N/A"}")
            Text(text = "Plot: ${movie.plot ?: "N/A"}")
        }
    }
}