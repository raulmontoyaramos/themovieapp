package com.raul.themovieapp.domain.usecase

import com.raul.themovieapp.domain.datasource.MovieLocalDataSource
import kotlinx.coroutines.flow.map

class ObserveMovieDetailsUseCase(
    private val movieLocalDataSource: MovieLocalDataSource,
) {
    fun observe(id: Int) =
        movieLocalDataSource.getMovieById(id).map {
            it
        }
}