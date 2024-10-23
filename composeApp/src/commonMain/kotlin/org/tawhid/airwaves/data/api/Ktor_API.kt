package org.tawhid.airwaves.data.api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "dfdsg"
private const val API_KEY = "sdf"

internal abstract class Ktor_API {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }


    fun HttpRequestBuilder.pathUrl(path : String) {
        url {
            takeFrom(BASE_URL)
            path("1",path)
            parameter("api_key", API_KEY)
        }
    }
}