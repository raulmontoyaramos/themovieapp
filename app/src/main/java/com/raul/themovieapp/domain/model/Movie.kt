package com.raul.themovieapp.domain.model

import java.time.LocalDate

data class Movie(
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
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
