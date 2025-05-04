//package com.eshan.filmapp.ui
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.eshan.filmapp.viewmodel.MovieViewModel
//
//@Composable
//fun WebTitleSearchScreen(movieViewModel: MovieViewModel = viewModel()) {
//    var query by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        OutlinedTextField(
//            value = query,
//            onValueChange = { query = it },
//            label = { Text("Enter movie title") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(
//            onClick = { movieViewModel.searchMoviesByTitleFromWeb(query) },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Search from Web")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        val results by movieViewModel.webSearchResults.collectAsState()
//
//        LazyColumn {
//            items(results) { movie ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(8.dp)) {
//                        Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
//                        Text(text = "Year: ${movie.year}")
//                    }
//                }
//            }
//        }
//    }
//}

package com.eshan.filmapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eshan.filmapp.viewmodel.MovieViewModel
import com.eshan.filmapp.util.observeAsState  // Make sure to import your extension

@Composable
fun WebTitleSearchScreen(movieViewModel: MovieViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Enter movie title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { movieViewModel.searchMoviesByTitleFromWeb(query) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search from Web")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val results by movieViewModel.webSearchResults.observeAsState(emptyList())

        LazyColumn {
            items(results) { movie ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Title: ${movie.title}")
                        Text("Year: ${movie.year}")
                     
                    }
                }
            }
        }
    }
}
