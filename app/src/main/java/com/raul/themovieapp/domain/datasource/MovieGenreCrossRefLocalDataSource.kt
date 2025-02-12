package com.raul.themovieapp.domain.datasource


interface MovieGenreCrossRefLocalDataSource {
    suspend fun insertMovieGenreIds(movieId: Int, genreId: Int)

}
