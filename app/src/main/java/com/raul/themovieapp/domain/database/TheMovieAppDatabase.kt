package com.raul.themovieapp.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raul.themovieapp.data.database.RoomTypeConverters
import com.raul.themovieapp.data.database.dao.GenreDao
import com.raul.themovieapp.data.database.dao.MovieDao
import com.raul.themovieapp.data.database.dao.MovieGenreCrossRefDao
import com.raul.themovieapp.data.database.model.GenreEntity
import com.raul.themovieapp.data.database.model.MovieEntity
import com.raul.themovieapp.data.database.model.MovieGenreCrossRefEntity

@Database(
    entities = [MovieEntity::class, GenreEntity::class, MovieGenreCrossRefEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class TheMovieAppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun movieGenreCrossRefDao(): MovieGenreCrossRefDao
}
