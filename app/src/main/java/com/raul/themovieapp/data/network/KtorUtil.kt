package com.raul.themovieapp.data.network

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.HttpClientCall
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpProtocolVersion
import io.ktor.http.HttpStatusCode
import io.ktor.util.date.GMTDate
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

const val baseUrl = "https://api.themoviedb.org/3/"

suspend inline fun HttpClient.safeGet(
    path: String,
    requestBuilder: HttpRequestBuilder.() -> Unit = {}
): HttpResponse = try {
    get(urlString = baseUrl + path, block = requestBuilder)
} catch (e: Exception) {
    NetworkFailureHttpResponse(e)
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

data class NetworkFailureHttpResponse(val exception: Exception) : HttpResponse() {
    override val call: HttpClientCall
        get() = throw IllegalStateException("Cannot access call on a network failure")

    // This field is internal to Ktor and is subject to have breaking changes in future versions.
    @InternalAPI
    override val rawContent: ByteReadChannel = ByteReadChannel.Empty

    override val coroutineContext: CoroutineContext = Dispatchers.Default

    override val headers: Headers = Headers.Empty

    override val requestTime: GMTDate = GMTDate.START

    override val responseTime: GMTDate = GMTDate.START

    override val status: HttpStatusCode = HttpStatusCode(-1, "A network exception occurred")

    override val version: HttpProtocolVersion = HttpProtocolVersion.HTTP_2_0
}

fun HttpResponse.failureReason(): String = when (this) {
    is NetworkFailureHttpResponse -> exception.failureReason()
    else -> "statusCode: ${status.value} \nurl: ${request.url}"
}

fun Throwable.failureReason(): String = "Exception: $message"

@OptIn(ExperimentalSerializationApi::class)
val sharedJson: Json by lazy {
    Json {
        isLenient = true
        ignoreUnknownKeys = true
        allowTrailingComma = true
        encodeDefaults = true
    }
}
