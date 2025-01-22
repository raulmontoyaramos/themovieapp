package com.raul.themovieapp.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raul.themovieapp.data.database.RoomTypeConverters
import com.raul.themovieapp.data.database.dao.MovieDao
import com.raul.themovieapp.data.database.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 0,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class TheMovieAppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var Instance: TheMovieAppDatabase? = null

        fun getDatabase(context: Context): TheMovieAppDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TheMovieAppDatabase::class.java, "movies_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
