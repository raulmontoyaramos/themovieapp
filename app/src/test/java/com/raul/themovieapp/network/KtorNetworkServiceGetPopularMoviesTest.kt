package com.raul.themovieapp.network

import arrow.core.left
import arrow.core.right
import com.raul.themovieapp.KtorTestRule
import com.raul.themovieapp.data.network.KtorNetworkService
import com.raul.themovieapp.domain.NetworkError
import com.raul.themovieapp.domain.model.Genre
import com.raul.themovieapp.domain.model.Movie
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class KtorNetworkServiceGetPopularMoviesTest {

    @get:Rule
    val networkTestRule = KtorTestRule()

    @Test
    fun `after receiving 200 return success list of movies`() = runTest {
        val json = """
            {
              "page": 1,
              "results": [
                {
                  "adult": false,
                  "backdrop_path": "/rDa3SfEijeRNCWtHQZCwfbGxYvR.jpg",
               "genres": [
                {
                  "id": 28,
                  "name": "Action"
                },
                {
                  "id": 878,
                  "name": "Science Fiction"
                },
                {
                  "id": 12,
                  "name": "Adventure"
                },
                {
                  "id": 14,
                  "name": "Fantasy"
                },
                {
                  "id": 53,
                  "name": "Thriller"
                }
              ],
                  "id": 539972,
                  "original_language": "en",
                  "original_title": "Kraven the Hunter",
                  "overview": "Kraven Kravinoff's complex relationship with his ruthless gangster father, Nikolai, starts him down a path of vengeance with brutal consequences, motivating him to become not only the greatest hunter in the world, but also one of its most feared.",
                  "popularity": 5481.159,
                  "poster_path": "/1GvBhRxY6MELDfxFrete6BNhBB5.jpg",
                  "release_date": "2024-12-11",
                  "title": "Kraven the Hunter",
                  "video": false,
                  "vote_average": 6.5,
                  "vote_count": 671
                }
                ],
              "total_pages": 48267,
              "total_results": 965332
            
            }
        """.trimIndent()

        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(200, json))

        val expectedResult = listOf(
            Movie(
                adult = false,
                backdropPath = "/rDa3SfEijeRNCWtHQZCwfbGxYvR.jpg",
                genres = listOf(
                    Genre(id = 28, name = "Action"),
                    Genre(id = 878, name = "Science Fiction"),
                    Genre(id = 12, name = "Adventure"),
                    Genre(id = 14, name = "Fantasy"),
                    Genre(id = 53, name = "Thriller")
                ),
                id = 539972,
                originalLanguage = "en",
                originalTitle = "Kraven the Hunter",
                overview = "Kraven Kravinoff's complex relationship with his ruthless gangster father, Nikolai, starts him down a path of vengeance with brutal consequences, motivating him to become not only the greatest hunter in the world, but also one of its most feared.",
                popularity = 5481.159,
                posterPath = "/1GvBhRxY6MELDfxFrete6BNhBB5.jpg",
                releaseDate = LocalDate.parse("2024-12-11"),
                runtime = null,
                title = "Kraven the Hunter",
                video = false,
                voteAverage = 6.5,
                voteCount = 671
            )
        ).right()

        networkService.getPopularMovies() shouldEqual expectedResult
    }

    @Test
    fun `after non 200 should return error`() = runTest {
        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(401))
        networkService.getPopularMovies() shouldEqual NetworkError.UnknownError.left()
    }

    @Test
    fun `after 200 should return success but returns error when deserialising date`() = runTest {
        val json = """
            {
              "page": 1,
              "results": [
                {
                  "adult": false,
                  "backdrop_path": "/rDa3SfEijeRNCWtHQZCwfbGxYvR.jpg",
                                "genres": [
                                  {
                                    "id": 28,
                                    "name": "Action"
                                  },
                                  {
                                    "id": 878,
                                    "name": "Science Fiction"
                                  },
                                  {
                                    "id": 12,
                                    "name": "Adventure"
                                  },
                                  {
                                    "id": 14,
                                    "name": "Fantasy"
                                  },
                                  {
                                    "id": 53,
                                    "name": "Thriller"
                                  }
                                ],
                  "id": 539972,
                  "original_language": "en",
                  "original_title": "Kraven the Hunter",
                  "overview": "Kraven Kravinoff's complex relationship with his ruthless gangster father, Nikolai, starts him down a path of vengeance with brutal consequences, motivating him to become not only the greatest hunter in the world, but also one of its most feared.",
                  "popularity": 5481.159,
                  "poster_path": "/1GvBhRxY6MELDfxFrete6BNhBB5.jpg",
                  "release_date": "2024--11",
                  "title": "Kraven the Hunter",
                  "video": false,
                  "vote_average": 6.5,
                  "vote_count": 671
                }
                ],
              "total_pages": 48267,
              "total_results": 965332
            }
        """.trimIndent()

        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(200, json))
        networkService.getPopularMovies() shouldEqual NetworkError.DeserialisationError.left()
    }
}
