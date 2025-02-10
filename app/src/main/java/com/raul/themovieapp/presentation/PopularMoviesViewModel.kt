package com.raul.themovieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raul.themovieapp.domain.model.Movie
import com.raul.themovieapp.domain.usecase.ObserveMoviesUseCase
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
    val observeMoviesUseCase: ObserveMoviesUseCase
) : ViewModel() {

    val viewState = MutableStateFlow(
        PopularMoviesViewState(
            movies = emptyList(),
            isLoading = true,
            isError = false
        )
    )

    init {
        syncMovies()
    }

    private fun syncMovies() {
        viewModelScope.launch {
            val resultsync = withContext(Dispatchers.IO) {
                syncMoviesUseCase.run()
            }
            resultsync.fold(
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
}

data class PopularMoviesViewState(
    val movies: List<Movie>,
    val isLoading: Boolean,
    val isError: Boolean,
)

class PopularMoviesViewModelFactory(
    private val syncMoviesUseCase: SyncMoviesUseCase,
    private val observeMoviesUseCase: ObserveMoviesUseCase
) {
    internal fun create() = viewModelFactory {
        PopularMoviesViewModel(
            syncMoviesUseCase = syncMoviesUseCase,
            observeMoviesUseCase = observeMoviesUseCase
        )
    }
}
