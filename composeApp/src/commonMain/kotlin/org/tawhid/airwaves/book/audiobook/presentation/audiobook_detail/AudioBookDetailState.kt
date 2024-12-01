package org.tawhid.airwaves.book.audiobook.presentation.audiobook_detail

import org.tawhid.airwaves.book.audiobook.domain.AudioBook
import org.tawhid.airwaves.book.audiobook.domain.AudioBookTracks
import org.tawhid.airwaves.core.presentation.utils.UiText

data class AudioBookDetailState(
    val isSaved: Boolean = false,
    val audioBook: AudioBook? = null,
    val errorAudioBookTracks: UiText? = null,
    val isLoadingAudioBookTracks: Boolean = false,
    val audioBookTracks: List<AudioBookTracks>? = null
)