package com.raul.themovieapp.domain.usecase

import com.raul.themovieapp.domain.datasource.MovieLocalDataSource

class ObserveMovieDetailsUseCase(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val id: Int
) {
    fun observe() =
        movieLocalDataSource.getMovieById(id)
}