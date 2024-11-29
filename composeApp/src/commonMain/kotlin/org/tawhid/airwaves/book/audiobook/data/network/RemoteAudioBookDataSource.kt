package org.tawhid.airwaves.book.audiobook.data.network

import org.tawhid.airwaves.book.audiobook.data.dto.SearchResponseDto
import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result

interface RemoteAudioBookDataSource {
    suspend fun searchAudioBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getScienceFictionBooks(
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getActionAdventureBooks(
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getEducationalBooks(
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>
}