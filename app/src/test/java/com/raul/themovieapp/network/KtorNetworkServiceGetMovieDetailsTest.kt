package com.raul.themovieapp.network

import arrow.core.left
import arrow.core.right
import com.raul.themovieapp.KtorTestRule
import com.raul.themovieapp.data.network.KtorNetworkService
import com.raul.themovieapp.domain.NetworkError
import com.raul.themovieapp.domain.model.Genre
import com.raul.themovieapp.domain.model.MovieDetails
import com.raul.themovieapp.domain.model.ProductionCompany
import com.raul.themovieapp.domain.model.ProductionCountry
import com.raul.themovieapp.domain.model.SpokenLanguage
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class KtorNetworkServiceGetMovieDetailsTest {

    @get:Rule
    val networkTestRule = KtorTestRule()

    @Test
    fun `after receiving 200 should return movie details`() = runTest {
        val json = """
            {
              "adult": false,
              "backdrop_path": "/v9Du2HC3hlknAvGlWhquRbeifwW.jpg",
              "belongs_to_collection": null,
              "budget": 120000000,
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
              "homepage": "https://www.kravenmovie.com",
              "id": 539972,
              "imdb_id": "tt8790086",
              "origin_country": [
                "US"
              ],
              "original_language": "en",
              "original_title": "Kraven the Hunter",
              "overview": "Kraven Kravinoff's complex relationship with his ruthless gangster father, Nikolai, starts him down a path of vengeance with brutal consequences, motivating him to become not only the greatest hunter in the world, but also one of its most feared.",
              "popularity": 2600.343,
              "poster_path": "/i47IUSsN126K11JUzqQIOi1Mg1M.jpg",
              "production_companies": [
                {
                  "id": 5,
                  "logo_path": "/71BqEFAF4V3qjjMPCpLuyJFB9A.png",
                  "name": "Columbia Pictures",
                  "origin_country": "US"
                },
                {
                  "id": 53462,
                  "logo_path": "/nx8B3Phlcse02w86RW4CJqzCnfL.png",
                  "name": "Matt Tolmach Productions",
                  "origin_country": "US"
                },
                {
                  "id": 14439,
                  "logo_path": null,
                  "name": "Arad Productions",
                  "origin_country": "US"
                }
              ],
              "production_countries": [
                {
                  "iso_3166_1": "US",
                  "name": "United States of America"
                }
              ],
              "release_date": "2024-12-11",
              "revenue": 59184643,
              "runtime": 127,
              "spoken_languages": [
                {
                  "english_name": "English",
                  "iso_639_1": "en",
                  "name": "English"
                },
                {
                  "english_name": "Russian",
                  "iso_639_1": "ru",
                  "name": "Pусский"
                },
                {
                  "english_name": "Turkish",
                  "iso_639_1": "tr",
                  "name": "Türkçe"
                }
              ],
              "status": "Released",
              "tagline": "Villains aren't born. They're made.",
              "title": "Kraven the Hunter",
              "video": false,
              "vote_average": 6.6,
              "vote_count": 881
            }
        """.trimIndent()

        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(200, json))

        val expectedResult = MovieDetails(
            adult = false,
            backdropPath = "/v9Du2HC3hlknAvGlWhquRbeifwW.jpg",
            belongsToCollection = null,
            budget = 120000000,
        genres = listOf(
            Genre(id = 28, name = "Action"),
            Genre(id = 878, name = "Science Fiction"),
            Genre(id = 12, name = "Adventure"),
            Genre(id = 14, name = "Fantasy"),
            Genre(id = 53, name = "Thriller")),
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
                ProductionCompany(
                    id = 5,
                    logoPath = "/71BqEFAF4V3qjjMPCpLuyJFB9A.png",
                    name = "Columbia Pictures",
                    originCountry = "US"
                ),
                ProductionCompany(
                    id = 53462,
                    logoPath = "/nx8B3Phlcse02w86RW4CJqzCnfL.png",
                    name = "Matt Tolmach Productions",
                    originCountry = "US"
                ),
                ProductionCompany(
                    id = 14439,
                    logoPath = null,
                    name = "Arad Productions",
                    originCountry = "US"
                )
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
        ).right()

        networkService.getMovieDetails(539972) shouldEqual expectedResult
    }

    @Test
    fun `after non 200 should return error`() = runTest {
        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(401))
        networkService.getMovieDetails(539972) shouldEqual NetworkError.UnknownError.left()
    }

    @Test
    fun `after 200 should return success but returns error when deserialising date`() = runTest{
        val json = """
            {
              "adult": false,
              "backdrop_path": "/v9Du2HC3hlknAvGlWhquRbeifwW.jpg",
              "belongs_to_collection": null,
              "budget": 120000000,
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
              "homepage": "https://www.kravenmovie.com",
              "id": 539972,
              "imdb_id": "tt8790086",
              "origin_country": [
                "US"
              ],
              "original_language": "en",
              "original_title": "Kraven the Hunter",
              "overview": "Kraven Kravinoff's complex relationship with his ruthless gangster father, Nikolai, starts him down a path of vengeance with brutal consequences, motivating him to become not only the greatest hunter in the world, but also one of its most feared.",
              "popularity": 2600.343,
              "poster_path": "/i47IUSsN126K11JUzqQIOi1Mg1M.jpg",
              "production_companies": [
                {
                  "id": 5,
                  "logo_path": "/71BqEFAF4V3qjjMPCpLuyJFB9A.png",
                  "name": "Columbia Pictures",
                  "origin_country": "US"
                },
                {
                  "id": 53462,
                  "logo_path": "/nx8B3Phlcse02w86RW4CJqzCnfL.png",
                  "name": "Matt Tolmach Productions",
                  "origin_country": "US"
                },
                {
                  "id": 14439,
                  "logo_path": null,
                  "name": "Arad Productions",
                  "origin_country": "US"
                }
              ],
              "production_countries": [
                {
                  "iso_3166_1": "US",
                  "name": "United States of America"
                }
              ],
              "release_date": "2024-12-",
              "revenue": 59184643,
              "runtime": 127,
              "spoken_languages": [
                {
                  "english_name": "English",
                  "iso_639_1": "en",
                  "name": "English"
                },
                {
                  "english_name": "Russian",
                  "iso_639_1": "ru",
                  "name": "Pусский"
                },
                {
                  "english_name": "Turkish",
                  "iso_639_1": "tr",
                  "name": "Türkçe"
                }
              ],
              "status": "Released",
              "tagline": "Villains aren't born. They're made.",
              "title": "Kraven the Hunter",
              "video": false,
              "vote_average": 6.6,
              "vote_count": 881
            }
        """.trimIndent()

        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(200, json))
        networkService.getMovieDetails(539972) shouldEqual NetworkError.DeserialisationError.left()
    }
}
