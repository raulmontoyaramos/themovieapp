package com.raul.themovieapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
object PopularMovies

@Serializable
@Parcelize
data class MovieDetails(
    val movieId: Int
) : Parcelable
