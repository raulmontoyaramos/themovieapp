package com.raul.themovieapp.domain.usecase

import com.raul.themovieapp.domain.datasource.MovieLocalDataSource

class ObserveMovieDetailsUseCase(
    private val movieLocalDataSource: MovieLocalDataSource,
) {
    fun observe(id: Int) =
        movieLocalDataSource.getMovieById(id)
}