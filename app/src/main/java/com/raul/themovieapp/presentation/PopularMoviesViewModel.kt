package com.raul.themovieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.raul.themovieapp.MovieDetails
import com.raul.themovieapp.domain.model.Movie
import com.raul.themovieapp.domain.usecase.ObserveMoviesUseCase
import com.raul.themovieapp.domain.usecase.SyncGenresUseCase
import com.raul.themovieapp.domain.usecase.SyncMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularMoviesViewModel(
    val syncMoviesUseCase: SyncMoviesUseCase,
    val observeMoviesUseCase: ObserveMoviesUseCase,
    val syncGenresUseCase: SyncGenresUseCase,
    val navController: NavController
) : ViewModel() {

    val viewState = MutableStateFlow(
        PopularMoviesViewState(
            movies = emptyList(),
            isLoading = true,
            isError = false
        )
    )

    init {
        syncGenres()
    }

    private fun syncGenres() {
        viewModelScope.launch {
            val resultSync = withContext(Dispatchers.IO) {
                syncGenresUseCase.run()
            }
            resultSync.fold(
                ifLeft = {
                    println("SyncGenresUseCase - Error")
                },
                ifRight = {
                    syncMovies()
                }
            )
        }
    }

    private fun syncMovies() {
        viewModelScope.launch {
            val resultSync = withContext(Dispatchers.IO) {
                syncMoviesUseCase.run()
            }
            resultSync.fold(
                ifLeft = {
                    println("SyncMoviesUseCase - Error")
                    viewState.update {
                        it.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                },
                ifRight = {
                    observeMoviesUseCase.observe()
                        .flowOn(Dispatchers.IO)
                        .catch { println("Error ${it.message}") }
                        .onEach { movies ->
                            viewState.update {
                                it.copy(
                                    movies = movies,
                                    isLoading = false
                                )
                            }
                        }.launchIn(viewModelScope)
                }
            )
        }
    }

    fun onMovieClicked(movieId: Int) {
        println("PopularMoviesViewModel - onMovieClicked - movieId = $movieId")
        navController.navigate(MovieDetails(movieId))
    }
}



data class PopularMoviesViewState(
    val movies: List<Movie>,
    val isLoading: Boolean,
    val isError: Boolean,
)

class PopularMoviesViewModelFactory(
    private val syncMoviesUseCase: SyncMoviesUseCase,
    private val observeMoviesUseCase: ObserveMoviesUseCase,
    private val syncGenresUseCase: SyncGenresUseCase
) {
    internal fun create(
        navController: NavController
    ) = viewModelFactory {
        PopularMoviesViewModel(
            syncMoviesUseCase = syncMoviesUseCase,
            observeMoviesUseCase = observeMoviesUseCase,
            syncGenresUseCase = syncGenresUseCase,
            navController = navController
        )
    }
}
