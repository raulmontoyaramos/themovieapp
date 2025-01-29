package com.raul.themovieapp.domain

import arrow.core.Either
import com.raul.themovieapp.domain.model.Movie
import com.raul.themovieapp.domain.model.MovieDetails

interface NetworkService {

    suspend fun getPopularMovies(): Either<NetworkError, List<Movie>>

    suspend fun getMovieDetails(id: Int): Either<NetworkError, MovieDetails>
}

sealed class NetworkError {
    data object DeserialisationError : NetworkError()
    data object UnknownError : NetworkError()
}
