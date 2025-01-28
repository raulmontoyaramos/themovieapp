package com.raul.themovieapp.domain.usecase

import com.raul.themovieapp.domain.NetworkService
import com.raul.themovieapp.domain.datasource.MovieLocalDataSource

class SyncMoviesUseCase(
    private val networkService: NetworkService,
    private val movieLocalDataSource: MovieLocalDataSource
) {
    suspend fun run() =
        networkService.getPopularMovies()
            .map { movieLocalDataSource.insertMovies(it) }
}
