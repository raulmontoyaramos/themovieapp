package com.raul.themovieapp.domain.usecase

import com.raul.themovieapp.domain.datasource.MovieLocalDataSource

class ObserveMoviesUseCase(
    private val movieLocalDataSource: MovieLocalDataSource
) {
    fun observe() =
        movieLocalDataSource.getAllMovies()
}