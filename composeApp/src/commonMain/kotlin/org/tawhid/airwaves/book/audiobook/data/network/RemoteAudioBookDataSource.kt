package org.tawhid.airwaves.book.audiobook.data.network

import org.tawhid.airwaves.book.audiobook.data.dto.AudioBookTrackResponseDto
import org.tawhid.airwaves.book.audiobook.data.dto.SearchResponseDto
import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result

interface RemoteAudioBookDataSource {
    suspend fun searchAudioBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun fetchScienceFictionBooks(
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun fetchActionAdventureBooks(
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun fetchEducationalBooks(
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun fetchAudioBookTracks(
        audioBookId: String
    ): Result<AudioBookTrackResponseDto, DataError.Remote>
}