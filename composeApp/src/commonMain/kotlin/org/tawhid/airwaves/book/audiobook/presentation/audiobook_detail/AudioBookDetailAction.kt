package org.tawhid.airwaves.book.audiobook.presentation.audiobook_detail

import org.tawhid.airwaves.book.audiobook.domain.AudioBook

interface AudioBookDetailAction {
    data object OnBackClick : AudioBookDetailAction
    data object OnSaveClick : AudioBookDetailAction
    data class OnSelectedBookChange(val audioBook: AudioBook) : AudioBookDetailAction
}