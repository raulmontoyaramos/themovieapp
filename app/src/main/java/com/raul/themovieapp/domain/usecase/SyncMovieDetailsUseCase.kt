package com.raul.themovieapp.domain.usecase

import com.raul.themovieapp.domain.NetworkService
import com.raul.themovieapp.domain.datasource.MovieLocalDataSource

class SyncMovieDetailsUseCase(
    private val networkService: NetworkService,
    private val movieLocalDataSource: MovieLocalDataSource,
) {
    suspend fun run(id: Int) =
        networkService.getMovieDetails(id)
            .map { movieDetails ->
                movieDetails.runtime?.let { runtime ->
                    movieLocalDataSource.updateMovie(id, runtime)
                }
            }
}
