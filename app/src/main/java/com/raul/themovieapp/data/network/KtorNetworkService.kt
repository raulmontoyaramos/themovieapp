package com.raul.themovieapp.data.network

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import com.raul.themovieapp.data.network.model.NetworkMovieDetail
import com.raul.themovieapp.data.network.model.NetworkMovies
import com.raul.themovieapp.data.network.model.NetworkVideos
import com.raul.themovieapp.data.network.model.toDomain
import com.raul.themovieapp.domain.NetworkError
import com.raul.themovieapp.domain.NetworkService
import com.raul.themovieapp.domain.model.Movie
import com.raul.themovieapp.domain.model.MovieDetails
import com.raul.themovieapp.domain.model.Video
import io.ktor.client.HttpClient

class KtorNetworkService(
    private val client: HttpClient
) : NetworkService {

    override suspend fun getPopularMovies(): Either<NetworkError, List<Movie>> {
        val response = client.safeGet("movie/popular?language=en-US&page=1")

        return when (response.status.value) {
            200 -> response.safeReceive(NetworkMovies.serializer())
                .flatMap { it.toDomain() }
                .mapLeft { NetworkError.DeserialisationError }

            else -> NetworkError.UnknownError.left()
        }
    }

    override suspend fun getMovieDetails(id: Int): Either<NetworkError, MovieDetails> {
        val response = client.safeGet("movie/${id.toString()}?language=en-US")

        return when (response.status.value) {
            200 -> response.safeReceive(NetworkMovieDetail.serializer())
                .flatMap { it.toDomain() }
                .mapLeft { NetworkError.DeserialisationError }

            else -> NetworkError.UnknownError.left()
        }
    }

    override suspend fun getVideos(id: Int): Either<NetworkError, List<Video>> {
        val response = client.safeGet("movie/${id.toString()}/videos?language=en-US")

        return when (response.status.value) {
            200 -> response.safeReceive(NetworkVideos.serializer())
                .flatMap { it.toDomain() }
                .mapLeft { NetworkError.DeserialisationError }

            else -> NetworkError.UnknownError.left()
        }
    }

}
