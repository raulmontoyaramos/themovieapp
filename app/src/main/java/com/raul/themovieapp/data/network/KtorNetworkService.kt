package com.raul.themovieapp.data.network

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import com.raul.themovieapp.data.network.model.NetworkMovies
import com.raul.themovieapp.data.network.model.toDomain
import com.raul.themovieapp.domain.NetworkService
import com.raul.themovieapp.domain.model.Movie
import io.ktor.client.HttpClient

class KtorNetworkService(
    private val client: HttpClient
) : NetworkService {

    override suspend fun getPopularMovies(): Either<NetworkService.Error, List<Movie>> {
        val response = client.safeGet("movie/popular?language=en-US&page=1")

        return when (response.status.value) {
            200 -> response.safeReceive(NetworkMovies.serializer())
                .flatMap { it.toDomain() }
                .mapLeft { NetworkService.Error(message = "Deserialisation error") }

            else -> NetworkService.Error(response.failureReason()).left()
        }
    }
}
