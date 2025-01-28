package com.raul.themovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.raul.themovieapp.presentation.PopularMoviesViewModel
import com.raul.themovieapp.presentation.PopularMoviesViewModelFactory
import com.raul.themovieapp.screens.PopularMoviesScreen
import com.raul.themovieapp.ui.theme.TheMovieAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val appComponent by lazy { (application as TheMovieApplication).appComponent }

    @Inject
    lateinit var popularMoviesViewModelFactory: PopularMoviesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        appComponent.inject(this)

        setContent {
            TheMovieAppTheme {
                val viewModel by viewModels<PopularMoviesViewModel> {
                    popularMoviesViewModelFactory.create()
                }
                PopularMoviesScreen(
                    viewModel.viewState.collectAsState().value
                )
            }
        }
    }
}
