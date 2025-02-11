package com.raul.themovieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raul.themovieapp.domain.model.Cast
import com.raul.themovieapp.domain.model.Movie
import com.raul.themovieapp.domain.usecase.ObserveMovieDetailsUseCase
import com.raul.themovieapp.domain.usecase.SyncMovieDetailsUseCase
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
        syncMovieDetails(id)
    }

    private fun syncMovieDetails(id: Int) {
        viewModelScope.launch {
            val resultsync = withContext(Dispatchers.IO) {
                syncMovieDetailsUseCase.run(id)
            }
            resultsync.fold(
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
    private val observeMoviesUseCase: ObserveMovieDetailsUseCase
) {
    internal fun create(
        id: Int
    ) = viewModelFactory {
        MovieDetailsViewModel(
            syncMovieDetailsUseCase = syncMovieDetailsUseCase,
            observeMovieDetailsUseCase = observeMoviesUseCase,
            id = id
        )
    }
}
