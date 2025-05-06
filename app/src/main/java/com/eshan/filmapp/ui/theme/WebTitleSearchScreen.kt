package com.eshan.filmapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eshan.filmapp.viewmodel.MovieViewModel
import com.eshan.filmapp.util.observeAsState

@Composable
fun WebTitleSearchScreen(movieViewModel: MovieViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = Color.White,
        contentColor = Color.Black
    )


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
            )
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Enter movie title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { movieViewModel.searchMoviesByTitleFromWeb(query) },
                colors = buttonColors,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search from Web")
            }

            Spacer(modifier = Modifier.height(16.dp))

            val results by movieViewModel.webSearchResults.observeAsState(emptyList())

            LazyColumn {
                items(results) { movie ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE8F5E9)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Title: ${movie.title}")
                            Text("Year: ${movie.year}")
                        }
                    }
                }
            }

        }
    }
}

