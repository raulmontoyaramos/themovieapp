package com.raul.themovieapp.domain

import arrow.core.Either
import com.raul.themovieapp.domain.model.Movie
import com.raul.themovieapp.domain.model.MovieDetails
import com.raul.themovieapp.domain.model.Video

interface NetworkService {

    suspend fun getPopularMovies(): Either<NetworkError, List<Movie>>

    suspend fun getMovieDetails(id: Int): Either<NetworkError, MovieDetails>

    suspend fun getVideos(id: Int): Either<NetworkError, List<Video>>
}

sealed class NetworkError {
    data object DeserialisationError : NetworkError()
    data object UnknownError : NetworkError()
}
