package org.tawhid.airwaves.book.openbook.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.tawhid.airwaves.BuildKonfig
import org.tawhid.airwaves.book.openbook.data.dto.BookWorkDto
import org.tawhid.airwaves.book.openbook.data.dto.SearchResponseDto
import org.tawhid.airwaves.book.openbook.data.dto.TrendingResponseDto
import org.tawhid.airwaves.core.data.network.safeCall
import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result
import org.tawhid.airwaves.core.gemini.dto.ContentItem
import org.tawhid.airwaves.core.gemini.dto.GeminiResponseDto
import org.tawhid.airwaves.core.gemini.dto.RequestBody
import org.tawhid.airwaves.core.gemini.dto.RequestPart
import org.tawhid.airwaves.core.utils.GEMINI_BASE_URL
import org.tawhid.airwaves.core.utils.GEMINI_FLASH
import org.tawhid.airwaves.core.utils.OPEN_LIBRARY_BASE_URL
import org.tawhid.airwaves.core.utils.USER_AGENT

class RemoteBookDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${OPEN_LIBRARY_BASE_URL}/search.json"
            ) {
                header("User-Agent", USER_AGENT)
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter(
                    "fields",
                    "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
                )
            }
        }
    }

    override suspend fun fetchBookSummary(prompt: String): Result<GeminiResponseDto, DataError.Remote> {
        return safeCall<GeminiResponseDto> {
            val requestBody = RequestBody(
                contents = listOf(
                    ContentItem(
                        parts = listOf(RequestPart(text = prompt))
                    )
                )
            )
            httpClient.post(
                urlString = "${GEMINI_BASE_URL}/v1beta/models/${GEMINI_FLASH}:generateContent"
            ) {
                parameter("key", BuildKonfig.GEMINI_API_KEY)
                setBody(
                    Json.encodeToString(requestBody)
                )
            }
        }
    }

    override suspend fun fetchBookDescription(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(
                urlString = "${OPEN_LIBRARY_BASE_URL}/works/$bookWorkId.json"
            ) {
                header("User-Agent", USER_AGENT)
            }
        }
    }

    override suspend fun fetchTrendingBooks(): Result<TrendingResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${OPEN_LIBRARY_BASE_URL}/trending/daily.json"
            ) {
                header("User-Agent", USER_AGENT)
            }
        }
    }
}