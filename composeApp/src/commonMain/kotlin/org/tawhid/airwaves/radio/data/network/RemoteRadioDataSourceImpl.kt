package org.tawhid.airwaves.radio.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import org.tawhid.airwaves.core.data.network.safeCall
import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result
import org.tawhid.airwaves.core.utils.RADIO_BROWSER_BASE_URL
import org.tawhid.airwaves.core.utils.USER_AGENT
import org.tawhid.airwaves.radio.data.dto.RadioSearchResponseDto

class RemoteRadioDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteRadioDataSource {
    override suspend fun searchRadio(
        query: String,
        resultLimit: Int?
    ): Result<List<RadioSearchResponseDto>, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${RADIO_BROWSER_BASE_URL}/stations/search"
            ) {
                header("User-Agent", USER_AGENT)
                parameter("name", query)
                parameter("limit", resultLimit)
                parameter("hidebroken", "true")
                parameter("order", "clickcount")
            }
        }
    }

    override suspend fun fetchTrendingRadios(resultLimit: Int?): Result<List<RadioSearchResponseDto>, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${RADIO_BROWSER_BASE_URL}/stations/search"
            ) {
                header("User-Agent", USER_AGENT)
                parameter("limit", resultLimit)
                parameter("hidebroken", "true")
                parameter("order", "clickcount")
                parameter("reverse", "true")
            }
        }
    }

    override suspend fun fetchVerifiedRadios(resultLimit: Int?): Result<List<RadioSearchResponseDto>, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${RADIO_BROWSER_BASE_URL}/stations/search"
            ) {
                header("User-Agent", USER_AGENT)
                parameter("limit", resultLimit)
                parameter("has_extended_info", "true")
                parameter("hidebroken", "true")
                parameter("order", "random")
                parameter("reverse", "true")
            }
        }
    }

    override suspend fun fetchLatestRadios(resultLimit: Int?): Result<List<RadioSearchResponseDto>, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${RADIO_BROWSER_BASE_URL}/stations/search"
            ) {
                header("User-Agent", USER_AGENT)
                parameter("limit", resultLimit)
                parameter("reverse", "true")
                parameter("hidebroken", "true")
                parameter("order", "changetimestamp")
            }
        }
    }
}