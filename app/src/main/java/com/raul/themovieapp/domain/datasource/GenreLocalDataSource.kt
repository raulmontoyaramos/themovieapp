package com.raul.themovieapp.domain.datasource

import com.raul.themovieapp.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenreLocalDataSource {
    suspend fun insertGenres(genres: List<Genre>)

    fun getAllGenres(): Flow<List<Genre>>

    suspend fun getGenreById(genreId: Int): Genre?
}
