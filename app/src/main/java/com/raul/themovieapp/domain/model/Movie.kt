package com.raul.themovieapp.domain.model

import java.time.LocalDate

data class Movie(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genres: List<Genre>,
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

data class Genre(
    val id: Int,
    val name: String
)
