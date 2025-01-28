package com.raul.themovieapp.domain

import arrow.core.Either
import com.raul.themovieapp.domain.model.Movie

interface NetworkService {

    suspend fun getPopularMovies(): Either<Error, List<Movie>>

    data class Error(val message: String)
}