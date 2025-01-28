package com.raul.themovieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.raul.themovieapp.domain.NetworkService
import com.raul.themovieapp.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularMoviesViewModel(
    val networkService: NetworkService
) : ViewModel() {

    val viewState = MutableStateFlow(
        PopularMoviesViewState(
            movies = emptyList(),
            isLoading = true,
            isError = false
        )
    )

    init {
        viewModelScope.launch {
            val result: Either<NetworkService.Error, List<Movie>> = withContext(Dispatchers.IO) {
                networkService.getPopularMovies()
            }
            result.fold(
                ifLeft = { error ->
                    println("Error: $error")
                    viewState.update {
                        it.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                },
                ifRight = { movies ->
                    println("success: $movies")
                    viewState.update {
                        it.copy(
                            movies = movies,
                            isLoading = false
                        )
                    }
                }
            )
        }
    }

}

data class PopularMoviesViewState(
    val movies: List<Movie>,
    val isLoading: Boolean,
    val isError: Boolean,
)

class PopularMoviesViewModelFactory(
    private val networkService: NetworkService
) {
    internal fun create() = viewModelFactory {
        PopularMoviesViewModel(
            networkService = networkService
        )
    }
}
