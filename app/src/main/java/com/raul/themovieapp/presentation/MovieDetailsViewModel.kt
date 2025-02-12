package com.raul.themovieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.raul.themovieapp.domain.NetworkError
import com.raul.themovieapp.domain.model.Cast
import com.raul.themovieapp.domain.model.Movie
import com.raul.themovieapp.domain.usecase.ObserveMovieDetailsUseCase
import com.raul.themovieapp.domain.usecase.SyncMovieDetailsUseCase
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

class MovieDetailsViewModel(
    val syncMovieDetailsUseCase: SyncMovieDetailsUseCase,
    val syncMoviesUseCase: SyncMoviesUseCase,
    observeMovieDetailsUseCase: ObserveMovieDetailsUseCase,
    id: Int
) : ViewModel() {

    val viewState = MutableStateFlow(
        MovieDetailsViewState(
            movie = null,
            cast = emptyList(),
        )
    )

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                syncMoviesUseCase.run()
            }.fold({}, { syncMovieDetails(id) })
        }
        observeMovieDetailsUseCase.observe(id)
            .flowOn(Dispatchers.IO)
            .catch { println("Error ${it.message}") }
            .onEach { movie: Movie? ->
                viewState.update {
                    it.copy(
                        movie = movie
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun syncMovieDetails(id: Int) {
        viewModelScope.launch {
            val resultSync: Either<NetworkError, Unit?> = withContext(Dispatchers.IO) {
                syncMovieDetailsUseCase.run(id)
            }
            resultSync.fold(
                ifLeft = {
                    println("SyncMovieDetailsUseCase - Error")
//                    viewState.update {
//                        it.copy(
//                            isLoading = false,
//                            isError = true
//                        )
//                    }
                },
                ifRight = {}
            )
        }
    }
}

data class MovieDetailsViewState(
    val movie: Movie?,
    val cast: List<Cast>,
)

class MovieDetailsViewModelFactory(
    private val syncMovieDetailsUseCase: SyncMovieDetailsUseCase,
    private val syncMoviesUseCase: SyncMoviesUseCase,
    private val observeMoviesUseCase: ObserveMovieDetailsUseCase
) {
    internal fun create(
        id: Int
    ) = viewModelFactory {
        MovieDetailsViewModel(
            syncMovieDetailsUseCase = syncMovieDetailsUseCase,
            syncMoviesUseCase = syncMoviesUseCase,
            observeMovieDetailsUseCase = observeMoviesUseCase,
            id = id
        )
    }
}
