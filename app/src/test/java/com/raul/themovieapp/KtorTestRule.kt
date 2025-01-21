package com.raul.themovieapp

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import org.junit.rules.ExternalResource

class KtorTestRule: ExternalResource() {

    fun serverWithResponse(
        responseCode: Int,
        body: String = "",
        headers: Headers = Headers.build { set(HttpHeaders.ContentType, ContentType.Application.Json.toString()) },
        onRequestReceived: (HttpRequestData) -> Unit = {}
    ): HttpClient {
        return HttpClient(
            engine = MockEngine {
                onRequestReceived(it)

                respond(
                    content = body,
                    status = HttpStatusCode(responseCode, ""),
                    headers = headers
                )
            }
        ) { expectSuccess = false }
    }

    fun serverWithError(): HttpClient {
        return HttpClient(
            engine = MockEngine {
                error("Ya done goofed!")
            }
        ) { expectSuccess = false }
    }
}

fun HttpRequestData.requireBodyAsText() = (body as TextContent).text
