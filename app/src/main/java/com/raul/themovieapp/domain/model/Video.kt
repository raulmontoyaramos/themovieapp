package com.raul.themovieapp.domain.model

import java.time.Instant

data class Video(
    val iso6391: String,
    val iso31661: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val publishedAt: Instant,
    val id: String
)
