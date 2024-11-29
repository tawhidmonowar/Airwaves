package org.tawhid.airwaves.book.audiobook.presentation.audiobook_home

import org.tawhid.airwaves.book.audiobook.domain.AudioBook

interface AudioBookAction {
    data class OnSearchQueryChange(val query: String) : AudioBookAction
    data class OnAudioBookClick(val audioBook: AudioBook) : AudioBookAction
}