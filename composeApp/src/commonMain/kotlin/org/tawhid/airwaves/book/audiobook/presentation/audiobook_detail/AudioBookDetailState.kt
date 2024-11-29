package org.tawhid.airwaves.book.audiobook.presentation.audiobook_detail

import org.tawhid.airwaves.book.audiobook.domain.AudioBook
import org.tawhid.airwaves.book.audiobook.domain.AudioBookTracks

data class AudioBookDetailState(
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val audioBook: AudioBook? = null,
    val audioBookTracks: List<AudioBookTracks>? = null
)