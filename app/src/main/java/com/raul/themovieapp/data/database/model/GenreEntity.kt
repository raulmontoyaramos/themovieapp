package com.raul.themovieapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raul.themovieapp.domain.model.Genre

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey
    val genreId: Int,
    val name: String
)

fun GenreEntity.toDomain() =
    Genre(
        id = genreId,
        name = name
    )

fun Genre.toDatabase() =
    GenreEntity(
        genreId = id,
        name = name
    )
