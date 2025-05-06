package com.eshan.filmapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eshan.filmapp.viewmodel.SearchMovieViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.eshan.filmapp.util.observeAsState

@Composable
fun SearchMovieScreen(viewModel: SearchMovieViewModel = viewModel()) {
    var movieTitle by remember { mutableStateOf("") }


    val movieResult by viewModel.searchResult.observeAsState(initial = null)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFC8E6C9),
                        Color(0xFFE8F5E9)
                    )
                )
            ),

        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFC8E6C9),
                        Color(0xFFE8F5E9)
                    )
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),

    ) {
        OutlinedTextField(
            value = movieTitle,
            onValueChange = { movieTitle = it },
            label = { Text("Enter Movie Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
                Button(
                    onClick = { viewModel.retrieveMovie(movieTitle) },
                    colors = buttonColors
                ) {
                    Text("Retrieve Movie")
                }

                Button(
                    onClick = { viewModel.saveMovieToDatabase() },
                    colors = buttonColors
                ) {
                    Text("Save Movie to Database")
                }
            }
        }

        movieResult?.let { movie ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
                    .padding(top = 16.dp)
            ) {
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
}

