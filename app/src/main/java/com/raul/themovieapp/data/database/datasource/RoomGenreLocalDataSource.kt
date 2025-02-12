package com.raul.themovieapp.data.database.datasource

import com.raul.themovieapp.data.database.dao.GenreDao
import com.raul.themovieapp.data.database.model.toDatabase
import com.raul.themovieapp.data.database.model.toDomain
import com.raul.themovieapp.domain.datasource.GenreLocalDataSource
import com.raul.themovieapp.domain.model.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomGenreLocalDataSource(private val genreDao: GenreDao): GenreLocalDataSource {
    override suspend fun insertGenres(genres: List<Genre>) =
        genreDao.insertGenres(genres.map { it.toDatabase() })

    override fun getAllGenres(): Flow<List<Genre>> =
        genreDao.getAllGenres().map { genres -> genres.map { it.toDomain() } }

    override suspend fun getGenreById(genreId: Int): Genre? =
        genreDao.getGenreById(genreId)?.toDomain()
}
