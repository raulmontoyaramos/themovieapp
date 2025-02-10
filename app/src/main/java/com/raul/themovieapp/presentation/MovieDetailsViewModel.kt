package com.raul.themovieapp.presentation

import androidx.lifecycle.ViewModel
import com.raul.themovieapp.domain.model.Cast
import com.raul.themovieapp.domain.model.MovieDetails
import kotlinx.coroutines.flow.MutableStateFlow

class MovieDetailsViewModel : ViewModel() {

    val viewState = MutableStateFlow(
        MovieDetailsViewState(
            movieDetails = null,
            cast = emptyList(),
        )
    )
}

data class MovieDetailsViewState(
    val movieDetails: MovieDetails?,
    val cast: List<Cast>,
)
