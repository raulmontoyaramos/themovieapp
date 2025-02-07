package com.raul.themovieapp.network

import arrow.core.left
import arrow.core.right
import com.raul.themovieapp.KtorTestRule
import com.raul.themovieapp.data.network.KtorNetworkService
import com.raul.themovieapp.domain.NetworkError
import com.raul.themovieapp.domain.model.Cast
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class KtorNetworkServiceGetCastTest {

    @get:Rule
    val networkTestRule = KtorTestRule()

    @Test
    fun `after receiving 200 should return movie details`() = runTest {
        val json = """ 
            {
            "id": 539972,
            "cast": [
              {
                  "adult": false,
                  "gender": 2,
                  "id": 27428,
                  "known_for_department": "Acting",
                  "name": "Aaron Taylor-Johnson",
                  "original_name": "Aaron Taylor-Johnson",
                  "popularity": 48.703,
                  "profile_path": "/pFtHhih2XEaFaD3qOFyQW6q83br.jpg",
                  "cast_id": 8,
                  "character": "Sergei Kravinoff / Kraven the Hunter",
                  "credit_id": "60aec3f9d29bdd002c022ce0",
                  "order": 0
    }
    ],
            "crew": []
            }
        """.trimIndent()

        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(200, json))

        val expectedResult = listOf(
            Cast(
                adult = false,
                gender = 2,
                id = 27428,
                knownForDepartment = "Acting",
                name = "Aaron Taylor-Johnson",
                originalName = "Aaron Taylor-Johnson",
                popularity = 48.703,
                profilePath = "/pFtHhih2XEaFaD3qOFyQW6q83br.jpg",
                castId = 8,
                character = "Sergei Kravinoff / Kraven the Hunter",
                creditId = "60aec3f9d29bdd002c022ce0",
                order = 0
            )
        ).right()

        networkService.getCast(539972) shouldEqual expectedResult

    }

    @Test
    fun `after non 200 should return error`() = runTest {
        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(401))
        networkService.getCast(539972) shouldEqual NetworkError.UnknownError.left()
    }

    @Test
    fun `after 200 should return success but returns error when deserialising cast`() = runTest {
        val json = """ 
            {
              "id": 539972,
              "cast": [
                {
                  "adult": false,
                  "gender": 2,
                  "id": 12345,
                  "known_for_department": "Acting",
                  "name": "John Doe",
                  "original_name": "John Doe",
                  "popularity": 10.5,
                  "profile_path": "/path.jpg",
                  "cast_id": 1,
                  "character": "Hero",
                  "credit_id": null,
                  "order": 0
                }
              ],
              "crew": []
            }
        """.trimIndent()

        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(200, json))
        networkService.getCast(539972) shouldEqual NetworkError.DeserialisationError.left()
    }
}