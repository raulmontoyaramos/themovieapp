package com.raul.themovieapp.data.network.model

import com.raul.themovieapp.domain.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkGenres(
    @SerialName("genres")
    val genres: List<NetworkGenre>
)

@Serializable
data class NetworkGenre(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)

internal fun NetworkGenres.toDomain(): List<Genre> =
    genres.map { it.toDomain() }

internal fun NetworkGenre.toDomain() =
    Genre(
        id = id,
        name = name
    )
