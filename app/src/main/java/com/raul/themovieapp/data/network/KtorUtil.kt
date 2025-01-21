package com.raul.themovieapp.data.network

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

const val baseUrl = "https://api.themoviedb.org/3/"

suspend inline fun HttpClient.safeGet(
    path: String,
    requestBuilder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse = try {
    get(urlString = baseUrl + path, block = requestBuilder)
} catch (e: Exception) {
    throw e
}

suspend inline fun HttpClient.safeGet(
    path: String,
    json: String
): HttpResponse = safeGet(path = path) {
    setBody(TextContent(json, ContentType.Application.Json))
}

suspend inline fun <reified T> HttpResponse.safeReceive(
    serializer: KSerializer<T>,
    json: Json = sharedJson
) = Either.catch {
    val jsonString = bodyAsText()

    val jsonElement = json.parseToJsonElement(jsonString)

    json.decodeFromJsonElement(serializer, jsonElement)
}

@OptIn(ExperimentalSerializationApi::class)
val sharedJson: Json by lazy {
    Json {
        isLenient = true
        ignoreUnknownKeys = true
        allowTrailingComma = true
        encodeDefaults = true
    }
}
