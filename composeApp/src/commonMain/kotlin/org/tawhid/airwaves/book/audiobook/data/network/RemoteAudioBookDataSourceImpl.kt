package org.tawhid.airwaves.book.audiobook.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.tawhid.airwaves.book.audiobook.data.dto.AudioBookTrackResponseDto
import org.tawhid.airwaves.book.audiobook.data.dto.SearchResponseDto
import org.tawhid.airwaves.core.data.network.safeCall
import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result
import org.tawhid.airwaves.core.utils.LIBRI_VOX_BASE_URL

class RemoteAudioBookDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteAudioBookDataSource {
    override suspend fun searchAudioBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${LIBRI_VOX_BASE_URL}/audiobooks"
            ) {
                parameter("title", "^${query}")
                parameter("limit", resultLimit)
                parameter("format", "json")
            }
        }
    }

    override suspend fun fetchScienceFictionBooks(
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${LIBRI_VOX_BASE_URL}/audiobooks"
            ) {
                parameter("genre", "science fiction")
                parameter("limit", resultLimit)
                parameter("format", "json")
            }
        }
    }

    override suspend fun fetchActionAdventureBooks(
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${LIBRI_VOX_BASE_URL}/audiobooks"
            ) {
                parameter("genre", "action %26 adventure")
                parameter("limit", resultLimit)
                parameter("format", "json")
            }
        }
    }

    override suspend fun fetchEducationalBooks(
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${LIBRI_VOX_BASE_URL}/audiobooks"
            ) {
                parameter("genre", "education")
                parameter("limit", resultLimit)
                parameter("format", "json")
            }
        }
    }

    override suspend fun fetchAudioBookTracks(
        audioBookId: String
    ): Result<AudioBookTrackResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${LIBRI_VOX_BASE_URL}/audiotracks"
            ) {
                parameter("project_id", audioBookId)
                parameter("format", "json")
            }
        }
    }
}