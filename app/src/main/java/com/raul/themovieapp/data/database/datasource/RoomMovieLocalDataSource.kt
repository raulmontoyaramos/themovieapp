package com.raul.themovieapp.data.database.datasource

import com.raul.themovieapp.data.database.dao.MovieDao
import com.raul.themovieapp.data.database.model.toDatabase
import com.raul.themovieapp.data.database.model.toDomain
import com.raul.themovieapp.domain.datasource.MovieLocalDataSource
import com.raul.themovieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomMovieLocalDataSource(private val movieDao: MovieDao) : MovieLocalDataSource {

    override suspend fun insertMovies(movies: List<Movie>) =
        movieDao.insertMovies(movies.map { it.toDatabase() })

    override fun getAllMovies(): Flow<List<Movie>> =
        movieDao.getAllMovies().map { moviesWithGenres -> moviesWithGenres.map { it.toDomain() } }

    override fun getMovieById(id: Int): Flow<Movie?> =
        movieDao.getMovieWithGenres(id).map { movieWithGenres -> movieWithGenres?.toDomain() }

    override suspend fun updateMovie(id: Int, runtime: Int) =
        movieDao.updateMovie(id, runtime)


}
