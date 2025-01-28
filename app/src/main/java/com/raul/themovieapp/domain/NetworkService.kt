package com.raul.themovieapp.domain

import arrow.core.Either
import com.raul.themovieapp.domain.model.Movie

interface NetworkService {

    suspend fun getPopularMovies(): Either<NetworkError, List<Movie>>
}

sealed class NetworkError {
    data object DeserialisationError : NetworkError()
    data object UnknownError : NetworkError()
}
