package com.raul.themovieapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

fun formattedYear(releaseDate: String?): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
    val outputFormat = SimpleDateFormat("yyyy", Locale("en"))
    val formattedDate = releaseDate?.let {
        try {
            val date = inputFormat.parse(it)
            val formattedDate = date?.let { it1 -> outputFormat.format(it1) }
            formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
    return formattedDate
}

fun Int.minuteToTime(): String {
    var minute = this
    val hour = minute / 60
    minute %= 60
    return "$hour h $minute m"
}