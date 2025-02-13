package com.raul.themovieapp.domain.usecase

import com.raul.themovieapp.domain.NetworkService
import com.raul.themovieapp.domain.datasource.GenreLocalDataSource

class SyncGenresUseCase(
    private val networkService: NetworkService,
    private val genreLocalDataSource: GenreLocalDataSource,
) {
    suspend fun run() =
        networkService.getGenres()
            .map { genres ->
                genreLocalDataSource.insertGenres(genres)
            }
}
