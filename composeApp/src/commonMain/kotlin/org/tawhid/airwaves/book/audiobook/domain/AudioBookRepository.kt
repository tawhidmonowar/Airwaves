package org.tawhid.airwaves.book.audiobook.domain

import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result

interface AudioBookRepository {
    suspend fun searchAudioBooks(query: String): Result<List<AudioBook>, DataError.Remote>
    suspend fun getScienceFictionBooks(): Result<List<AudioBook>, DataError.Remote>
    suspend fun getActionAdventureBooks(): Result<List<AudioBook>, DataError.Remote>
    suspend fun getEducationalBooks(): Result<List<AudioBook>, DataError.Remote>
}