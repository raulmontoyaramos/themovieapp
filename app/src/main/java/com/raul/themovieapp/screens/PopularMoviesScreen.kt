package com.raul.themovieapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import com.raul.themovieapp.domain.model.Movie
import com.raul.themovieapp.presentation.PopularMoviesViewState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularMoviesScreen(
    viewState: PopularMoviesViewState
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Popular Movies") })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                viewState.isLoading ->
                    LoadingScreen()

                viewState.isError ->
                    ErrorScreen("Error loading movies")

                viewState.movies.isEmpty() ->
                    Empty("popular movies")

                else ->
                    MoviesGrid(
                        viewState = viewState
                    )
            }
        }

    }
}

@Composable
fun MoviesGrid(
    viewState: PopularMoviesViewState
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        columns = GridCells.Fixed(3),
        content = {
            items(viewState.movies) { movie ->
                MovieCard(
                    movie = movie
                )
            }
        })
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Loading items..")
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Error",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(6.dp),
            )

        }
    }
}

@Composable
fun Empty(subject: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "No $subject to show",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(6.dp),
            )
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .background(color = Color.White),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column {
            val data = "https://image.tmdb.org/t/p/original" + movie.posterPath
            println("URL = $data")
            println("Movie = $movie")
            SubcomposeAsyncImage(
//                imageLoader = imageLoader,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data)
                    .build(),
                contentDescription = movie.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                onError = {
                    println("error loading image: ${it.result}")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            val lineHeight = MaterialTheme.typography.bodyMedium.fontSize * 4 / 3
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 10.dp),
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                lineHeight = lineHeight
            )
            Text(
                text = movie.releaseDate.toString(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    PopularMoviesScreen(
        viewState = PopularMoviesViewState(
            movies = emptyList(),
            isLoading = true,
            isError = false
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    PopularMoviesScreen(
        viewState = PopularMoviesViewState(
            movies = emptyList(),
            isLoading = false,
            isError = true
        )
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyScreenPreview() {
    PopularMoviesScreen(
        viewState = PopularMoviesViewState(
            movies = emptyList(),
            isLoading = false,
            isError = false
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview() {
    PopularMoviesScreen(
        viewState = PopularMoviesViewState(
            movies = listOf(
                Movie(
                    adult = false,
                    backdropPath = "/rDa3SfEijeRNCWtHQZCwfbGxYvR.jpg",
                    genreIds = listOf(
                        28,
                        878,
                        12,
                        14,
                        53
                    ),
                    id = 539972,
                    originalLanguage = "en",
                    originalTitle = "Kraven the Hunter",
                    overview = "Kraven Kravinoff's complex relationship with his ruthless gangster father, Nikolai, starts him down a path of vengeance with brutal consequences, motivating him to become not only the greatest hunter in the world, but also one of its most feared.",
                    popularity = 5481.159,
                    posterPath = "/1GvBhRxY6MELDfxFrete6BNhBB5.jpg",
                    releaseDate = LocalDate.parse("2024-12-11"),
                    title = "Kraven the Hunter",
                    video = false,
                    voteAverage = 6.5,
                    voteCount = 671
                )
            ),
            isLoading = false,
            isError = false
        )
    )
}
