package com.raul.themovieapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.raul.themovieapp.domain.model.Genre
import com.raul.themovieapp.domain.model.MovieDetails
import com.raul.themovieapp.domain.model.ProductionCompany
import com.raul.themovieapp.domain.model.ProductionCountry
import com.raul.themovieapp.domain.model.SpokenLanguage
import com.raul.themovieapp.utils.formattedYear
import com.raul.themovieapp.utils.minuteToTime
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen() {
    val movieDetails = MovieDetails(
        adult = false,
        backdropPath = "/v9Du2HC3hlknAvGlWhquRbeifwW.jpg",
        belongsToCollection = null,
        budget = 120000000,
        genres = listOf(
            Genre(id = 28, name = "Action"),
            Genre(id = 878, name = "Science Fiction"),
            Genre(id = 12, name = "Adventure"),
            Genre(id = 14, name = "Fantasy"),
            Genre(id = 53, name = "Thriller")
        ),
        homepage = "https://www.kravenmovie.com",
        id = 539972,
        imdbId = "tt8790086",
        originCountry = listOf("US"),
        originalLanguage = "en",
        originalTitle = "Kraven the Hunter",
        overview = "Kraven Kravinoff's complex relationship with his ruthless gangster father, Nikolai, starts him down a path of vengeance with brutal consequences, motivating him to become not only the greatest hunter in the world, but also one of its most feared.",
        popularity = 2600.343,
        posterPath = "/i47IUSsN126K11JUzqQIOi1Mg1M.jpg",
        productionCompanies = listOf(
            ProductionCompany(id = 5, logoPath = "/71BqEFAF4V3qjjMPCpLuyJFB9A.png", name = "Columbia Pictures", originCountry = "US"),
            ProductionCompany(id = 53462, logoPath = "/nx8B3Phlcse02w86RW4CJqzCnfL.png", name = "Matt Tolmach Productions", originCountry = "US"),
            ProductionCompany(id = 14439, logoPath = null, name = "Arad Productions", originCountry = "US")
        ),
        productionCountries = listOf(
            ProductionCountry(iso31661 = "US", name = "United States of America")
        ),
        releaseDate = LocalDate.parse("2024-12-11"),
        revenue = 59184643,
        runtime = 127,
        spokenLanguages = listOf(
            SpokenLanguage(englishName = "English", iso6391 = "en", name = "English"),
            SpokenLanguage(englishName = "Russian", iso6391 = "ru", name = "Pусский"),
            SpokenLanguage(englishName = "Turkish", iso6391 = "tr", name = "Türkçe")
        ),
        status = "Released",
        tagline = "Villains aren't born. They're made.",
        title = "Kraven the Hunter",
        video = false,
        voteAverage = 6.6,
        voteCount = 881
    )
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Movie Details") })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column {
                ItemPoster(movieDetails)
                ItemTitle(movieDetails)
                ItemOverview(movieDetails)
            }
        }
    }
}

@Composable
fun ItemPoster(movieDetails: MovieDetails) {
    Box(modifier = Modifier.padding(horizontal = 15.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/original" + movieDetails.backdropPath)
                .crossfade(true)
                .build(),
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 60.dp)
                .clip(shape = RoundedCornerShape(10.dp))
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/original" + movieDetails.posterPath)
                .crossfade(true).build(),
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(120.dp)
                .height(160.dp)
                .align(Alignment.CenterStart)
                .clip(shape = RoundedCornerShape(10.dp))
        )
    }
}

@Composable
fun ItemTitle(movieDetails: MovieDetails) {

    Spacer(modifier = Modifier.height(20.dp))

    val title = movieDetails.title
    val year = formattedYear(movieDetails.releaseDate.toString())

    Text(
        text = buildAnnotatedString {
            append(title); append(" "); withStyle(style = SpanStyle(color = Color.Gray)) {
            append(
                "($year)"
            )
        }
        },
        style = MaterialTheme.typography.titleLarge,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Release:",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            modifier = Modifier.padding(end = 10.dp)
        )

        val originalLanguage = "(${movieDetails.originalLanguage.uppercase()})"
        Text(
            text = "${movieDetails.releaseDate} $originalLanguage ${movieDetails.runtime?.minuteToTime() ?: "Unknown duration"}",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            modifier = Modifier.padding(end = 10.dp)
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        val concatenatedGenres: String = movieDetails.genres.joinToString(", ") { it.name }
        Text(
            text = concatenatedGenres,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularProgress((movieDetails.voteAverage.toFloat().div(10)))

        Text(
            text = "User Score",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 10.dp, end = 15.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .height(20.dp)
                .width(3.dp)
                .background(Color.LightGray)
        )

        Row(
            modifier = Modifier
//                .clickable(onClick = {
//                    val item = videos.results?.last { it?.type == "Trailer" }
//                    navController.navigate(Screen.YoutubePlayerScreen.route + "youtubeCode=${item?.key}")
//                })
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play Arrow",
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "Play Trailer",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                modifier = Modifier.padding(end = 10.dp)
            )

        }
    }

}

@Composable
fun ItemOverview(movieDetails: MovieDetails) {
    Spacer(modifier = Modifier.height(15.dp))
    Text(
        text = "Overview",
        style = MaterialTheme.typography.titleLarge,
        maxLines = 1,
        modifier = Modifier.padding(start = 15.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))
    val lineHeight = MaterialTheme.typography.bodyLarge.fontSize * 4 / 3
    Text(
        text = movieDetails.overview,
        style = MaterialTheme.typography.bodyLarge,
        lineHeight = lineHeight,
        modifier = Modifier.padding(horizontal = 15.dp)
    )
}

@Composable
fun CircularProgress(progressValue: Float) {
    Box(
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .clip(shape = CircleShape)
            .background(color = Color.Black)
    ) {
        CircularProgressIndicator(
            progress = { progressValue },
            modifier = Modifier
                .align(Alignment.Center)
                .height(38.dp)
                .width(38.dp),
            color = Color.Yellow,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
        )
        val percentage = (progressValue * 100).toInt()
        Text(
            text = buildAnnotatedString {
                append("$percentage")
                withStyle(style = SpanStyle(fontSize = 10.sp)) {
                    append(
                        "%"
                    )
                }
            },
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 12.sp,
            maxLines = 1,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsScreenPreview() {
    MovieDetailsScreen()
}