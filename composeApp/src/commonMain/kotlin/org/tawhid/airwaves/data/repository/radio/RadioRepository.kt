package org.tawhid.airwaves.data.repository.radio

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.tawhid.airwaves.utils.BASE_URL_RADIO
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse


class RadioRepository {
    private val httpClient = HttpClient {
        defaultRequest {
            url(BASE_URL_RADIO)
            contentType(ContentType.Application.Json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 60_0000
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }

            }
        }
    }

    suspend fun getRadios() : HttpResponse {
        return httpClient.get {
            url("stations/search")
            parameter("limit", "100")
            parameter("hidebroken", "true")
            parameter("has_extended_info", "true")
            parameter("order","clickcount")
        }
    }
    suspend fun searchRadios(query: String) : HttpResponse {
        return httpClient.get {
            url("stations/search")
            parameter("limit", "10")
            parameter("hidebroken", "true")
            parameter("has_extended_info", "true")
            parameter("order","clickcount")
        }
    }
}