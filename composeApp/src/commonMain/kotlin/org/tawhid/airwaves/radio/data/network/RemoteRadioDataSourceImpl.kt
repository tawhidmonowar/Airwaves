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
}