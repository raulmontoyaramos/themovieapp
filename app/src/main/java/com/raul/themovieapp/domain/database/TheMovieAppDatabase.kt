package com.raul.themovieapp.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raul.themovieapp.data.database.RoomTypeConverters
import com.raul.themovieapp.data.database.dao.MovieDao
import com.raul.themovieapp.data.database.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class TheMovieAppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
