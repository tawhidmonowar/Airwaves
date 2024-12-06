package org.tawhid.airwaves.radio.data.network

import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result
import org.tawhid.airwaves.radio.data.dto.RadioSearchResponseDto

interface RemoteRadioDataSource {
    suspend fun searchRadio(
        query: String,
        resultLimit: Int? = 50
    ): Result<List<RadioSearchResponseDto>, DataError.Remote>

    suspend fun fetchTrendingRadios(
        resultLimit: Int? = 50
    ): Result<List<RadioSearchResponseDto>, DataError.Remote>

    suspend fun fetchVerifiedRadios(
        resultLimit: Int? = 50
    ): Result<List<RadioSearchResponseDto>, DataError.Remote>

    suspend fun fetchLatestRadios(
        resultLimit: Int? = 100
    ): Result<List<RadioSearchResponseDto>, DataError.Remote>
}