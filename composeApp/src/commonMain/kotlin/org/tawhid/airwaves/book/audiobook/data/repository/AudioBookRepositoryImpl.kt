package org.tawhid.airwaves.book.audiobook.data.repository

import org.tawhid.airwaves.book.audiobook.data.mappers.toAudioBook
import org.tawhid.airwaves.book.audiobook.data.mappers.toAudioBookTracks
import org.tawhid.airwaves.book.audiobook.data.network.RemoteAudioBookDataSource
import org.tawhid.airwaves.book.audiobook.domain.AudioBook
import org.tawhid.airwaves.book.audiobook.domain.AudioBookRepository
import org.tawhid.airwaves.book.audiobook.domain.AudioBookTracks
import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result
import org.tawhid.airwaves.core.domain.map

class AudioBookRepositoryImpl(
    private val remoteAudioBookDataSource: RemoteAudioBookDataSource
) : AudioBookRepository {
    override suspend fun searchAudioBooks(query: String): Result<List<AudioBook>, DataError.Remote> {
        return remoteAudioBookDataSource.searchAudioBooks(query).map { dto ->
            dto.results.map {
                it.toAudioBook()
            }
        }
    }

    override suspend fun getScienceFictionBooks(): Result<List<AudioBook>, DataError.Remote> {
        return remoteAudioBookDataSource.fetchScienceFictionBooks().map { dto ->
            dto.results.map {
                it.toAudioBook()
            }
        }
    }

    override suspend fun getActionAdventureBooks(): Result<List<AudioBook>, DataError.Remote> {
        return remoteAudioBookDataSource.fetchActionAdventureBooks().map { dto ->
            dto.results.map {
                it.toAudioBook()
            }
        }
    }

    override suspend fun getEducationalBooks(): Result<List<AudioBook>, DataError.Remote> {
        return remoteAudioBookDataSource.fetchEducationalBooks().map { dto ->
            dto.results.map {
                it.toAudioBook()
            }
        }
    }

    override suspend fun getAudioBookTracks(audioBookId: String): Result<List<AudioBookTracks>, DataError.Remote> {
        return remoteAudioBookDataSource.fetchAudioBookTracks(audioBookId).map { dto ->
            dto.audioBookTracks.map {
                it.toAudioBookTracks()
            }
        }
    }
}