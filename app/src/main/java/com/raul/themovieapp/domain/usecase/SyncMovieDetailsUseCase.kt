package com.raul.themovieapp.domain.usecase

import com.raul.themovieapp.domain.NetworkService
import com.raul.themovieapp.domain.datasource.MovieLocalDataSource

class SyncMovieDetailsUseCase(
    private val networkService: NetworkService,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val id: Int
) {
    suspend fun run() =
        networkService.getMovieDetails(id)
            .map { movieLocalDataSource.updateMovie(id, it.runtime) }
}
