package com.raul.themovieapp.data.database.datasource

import com.raul.themovieapp.data.database.dao.MovieGenreCrossRefDao
import com.raul.themovieapp.data.database.model.MovieGenreCrossRefEntity
import com.raul.themovieapp.domain.datasource.MovieGenreCrossRefLocalDataSource

class RoomMovieGenreCrossRefLocalDataSource(private val movieGenreCrossRefDao: MovieGenreCrossRefDao) :
    MovieGenreCrossRefLocalDataSource {

    override suspend fun insertMovieGenreIds(movieId: Int, genreId: Int) {
        val movieGenreCrossRefEntity = MovieGenreCrossRefEntity(movieId, genreId)
        movieGenreCrossRefDao.insertMovieGenreIds(movieGenreCrossRefEntity)
    }
}
