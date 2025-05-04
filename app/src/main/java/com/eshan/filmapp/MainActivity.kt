package com.eshan.filmapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eshan.filmapp.ui.theme.SearchMovieScreen
import androidx.navigation.NavController
import com.eshan.filmapp.ui.WebTitleSearchScreen
import com.eshan.filmapp.ui.theme.ActorSearchScreen
import com.eshan.filmapp.viewmodel.MovieViewModel

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent { MainMenuScreen() }
//        }
//
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun MainMenuScreen(navController: NavController,movieViewModel: MovieViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                // TODO: Navigate to Add Movies to DB screen
                movieViewModel.insertHardcodedMovies()
            }) {
                Text("Add Movies to DB")
            }

            Button(onClick = {
                navController.navigate("search_movie")
            }) {
                Text("Search for Movies")
            }

            Button(onClick = {
                // TODO: Navigate to Search for Actors screen
                navController.navigate("actor_search")
            }) {
                Text("Search for Actors")
            }

            Button(
                onClick = { navController.navigate("web_title_search") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search Movie Titles from Web")
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") {
            MainMenuScreen(navController)
        }
        composable("search_movie") {
            SearchMovieScreen()
        }
        // You can later add "add_movie", "search_actors", etc.
        composable("actor_search") {
            ActorSearchScreen(onBack = { navController.popBackStack() })
        }

        composable("web_title_search") {
            WebTitleSearchScreen()
        }
    }
}

