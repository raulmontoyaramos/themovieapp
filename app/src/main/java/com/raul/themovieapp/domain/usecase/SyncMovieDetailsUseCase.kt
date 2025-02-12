package com.raul.themovieapp.domain.usecase

import com.raul.themovieapp.domain.NetworkService
import com.raul.themovieapp.domain.datasource.GenreLocalDataSource
import com.raul.themovieapp.domain.datasource.MovieGenreCrossRefLocalDataSource
import com.raul.themovieapp.domain.datasource.MovieLocalDataSource

class SyncMovieDetailsUseCase(
    private val networkService: NetworkService,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieGenreCrossRefLocalDataSource: MovieGenreCrossRefLocalDataSource,
    private val genreLocalDataSource: GenreLocalDataSource
) {
    suspend fun run(id: Int) =
        networkService.getMovieDetails(id)
            .map { movieDetails ->
                genreLocalDataSource.insertGenres(movieDetails.genres)
                movieDetails.genres.map { (genreId, _) ->
                    movieGenreCrossRefLocalDataSource.insertMovieGenreIds(movieDetails.id, genreId)
                }
                movieDetails.runtime?.let { runtime ->
                    movieLocalDataSource.updateMovie(id, runtime)
                }
            }
}
