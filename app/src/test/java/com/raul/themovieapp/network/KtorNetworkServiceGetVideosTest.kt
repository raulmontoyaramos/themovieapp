package com.raul.themovieapp.network

import arrow.core.left
import arrow.core.right
import com.raul.themovieapp.KtorTestRule
import com.raul.themovieapp.data.network.KtorNetworkService
import com.raul.themovieapp.domain.NetworkError
import com.raul.themovieapp.domain.model.Video
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.Instant

class KtorNetworkServiceGetVideosTest {

    @get:Rule
    val networkTestRule = KtorTestRule()

    @Test
    fun `after receiving 200 should return movie details`() = runTest {
        val json = """ 
            {
  "id": 539972,
  "results": [
    {
      "iso_639_1": "en",
      "iso_3166_1": "US",
      "name": "5 Minute Extended Preview",
      "key": "hDg7Zl_zMB4",
      "site": "YouTube",
      "size": 1080,
      "type": "Clip",
      "official": true,
      "published_at": "2025-01-14T18:00:24.000Z",
      "id": "678832f579cf13ee41ad0689"
    }
    ]
    }
        """.trimIndent()

        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(200, json))

        val expectedResult = listOf(
            Video(
                iso6391 = "en",
                iso31661 = "US",
                name = "5 Minute Extended Preview",
                key = "hDg7Zl_zMB4",
                site = "YouTube",
                size = 1080,
                type = "Clip",
                official = true,
                publishedAt = Instant.parse("2025-01-14T18:00:24.000Z"),
                id = "678832f579cf13ee41ad0689"
            )
        ).right()

        networkService.getVideos(539972) shouldEqual expectedResult
    }

    @Test
    fun `after non 200 should return error`() = runTest {
        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(401))
        networkService.getVideos(539972) shouldEqual NetworkError.UnknownError.left()
    }

    @Test
    fun `after 200 should return success but returns error when deserialising date`() = runTest {
        val json = """ 
            {
  "id": 539972,
  "results": [
    {
      "iso_639_1": "en",
      "iso_3166_1": "US",
      "name": "5 Minute Extended Preview",
      "key": "hDg7Zl_zMB4",
      "site": "YouTube",
      "size": 1080,
      "type": "Clip",
      "official": true,
      "published_at": "2025--14T18:00:24.000Z",
      "id": "678832f579cf13ee41ad0689"
    }
    ]
    }
        """.trimIndent()

        val networkService = KtorNetworkService(networkTestRule.serverWithResponse(200, json))
        networkService.getVideos(539972) shouldEqual NetworkError.DeserialisationError.left()
    }
}
