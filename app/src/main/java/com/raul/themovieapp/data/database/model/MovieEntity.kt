package com.raul.themovieapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raul.themovieapp.domain.model.Movie
import java.time.LocalDate

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: LocalDate,
    val runtime: Int?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

fun MovieEntity.toDomain() =
    Movie(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )

fun Movie.toDatabase() =
    MovieEntity(
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
