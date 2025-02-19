package com.raul.themovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.raul.themovieapp.presentation.MovieDetailsViewModel
import com.raul.themovieapp.presentation.MovieDetailsViewModelFactory
import com.raul.themovieapp.presentation.PopularMoviesViewModel
import com.raul.themovieapp.presentation.PopularMoviesViewModelFactory
import com.raul.themovieapp.screens.MovieDetailsScreen
import com.raul.themovieapp.screens.PopularMoviesScreen
import com.raul.themovieapp.ui.theme.TheMovieAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val appComponent by lazy { (application as TheMovieApplication).appComponent }

    @Inject
    lateinit var popularMoviesViewModelFactory: PopularMoviesViewModelFactory

    @Inject
    lateinit var movieDetailsViewModelFactory: MovieDetailsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        val appComponent = (application as TheMovieApplication).appComponent
        appComponent.inject(this)

        setContent {
            TheMovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController: NavHostController = rememberNavController()
                    Scaffold { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = PopularMovies,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable<PopularMovies> {
                                val viewModel =
                                    viewModel<PopularMoviesViewModel>(
                                        factory = popularMoviesViewModelFactory.create(
                                            navController = navController
                                        )
                                    )
                                PopularMoviesScreen(
                                    viewState = viewModel.viewState.collectAsState().value,
                                    onMovieClicked = viewModel::onMovieClicked
                                )
                            }
                            composable<MovieDetails> {
                                val movieId = it.toRoute<MovieDetails>().movieId
                                println("MovieDetails - movieId = $movieId")

                                val viewModel =
                                    viewModel<MovieDetailsViewModel>(
                                        factory = movieDetailsViewModelFactory.create(
                                            navController = navController,
                                            id = movieId
                                        )
                                    )
                                MovieDetailsScreen(
                                    viewState = viewModel.viewState.collectAsState().value,
                                    onBackButtonClicked = viewModel::onBackButtonClicked
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
