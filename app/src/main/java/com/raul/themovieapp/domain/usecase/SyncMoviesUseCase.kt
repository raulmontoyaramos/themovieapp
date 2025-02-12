package com.raul.themovieapp.domain.usecase

import com.raul.themovieapp.domain.NetworkService
import com.raul.themovieapp.domain.datasource.MovieGenreCrossRefLocalDataSource
import com.raul.themovieapp.domain.datasource.MovieLocalDataSource
import com.raul.themovieapp.domain.model.Movie

class SyncMoviesUseCase(
    private val networkService: NetworkService,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieGenreCrossRefLocalDataSource: MovieGenreCrossRefLocalDataSource,
) {
    suspend fun run() =
        networkService.getPopularMovies()
            .map { movieList: List<Pair<Movie, List<Int>>> ->
                movieLocalDataSource.insertMovies(movieList.map { it.first })
//                movieList.map { (movie, genreIds) ->
//                    genreIds.map {
//                        movieGenreCrossRefLocalDataSource.insertMovieGenreIds(movie.id, it)
//                    }
//                }
            }
}
