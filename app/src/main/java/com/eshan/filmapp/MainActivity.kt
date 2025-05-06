package com.eshan.filmapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
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
    val customFont = FontFamily(Font(R.font.lobster_regular))
    Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFCDD2),
                            Color(0xFFFFEBEE)
                        )
                    )
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Welcome to the Movie API App",
                    fontFamily = customFont,
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
                Button(
                    onClick = {
                        movieViewModel.insertHardcodedMovies()
                    }, colors = buttonColors
                ) {
                    Text("Add Movies to DB")
                }

                Button(
                    onClick = {
                        navController.navigate("search_movie")
                    }, colors = buttonColors
                ) {
                    Text("Search for Movies")
                }

                Button(
                    onClick = {
                        navController.navigate("actor_search")
                    }, colors = buttonColors
                ) {
                    Text("Search for Actors")
                }

                Button(
                    onClick = { navController.navigate("web_title_search") },
                    colors = buttonColors
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

        composable("actor_search") {
            ActorSearchScreen(onBack = { navController.popBackStack() })
        }

        composable("web_title_search") {
            WebTitleSearchScreen()
        }
    }
}

