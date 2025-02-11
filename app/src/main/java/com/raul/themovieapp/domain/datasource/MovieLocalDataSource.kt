package com.raul.themovieapp.domain.datasource

import com.raul.themovieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    suspend fun insertMovies(movies: List<Movie>)

    fun getAllMovies(): Flow<List<Movie>>

    fun getMovieById(id: Int): Flow<Movie?>

    suspend fun updateMovie(id: Int, runtime: Int)
}
