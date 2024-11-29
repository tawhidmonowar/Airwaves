package org.tawhid.airwaves.book.audiobook.presentation.audiobook_home

import org.tawhid.airwaves.book.audiobook.domain.AudioBook
import org.tawhid.airwaves.core.presentation.utils.UiText

data class AudioBookState(
    val searchQuery: String = "",
    val errorMsgSearch: UiText? = null,
    val errorMsgScienceFiction: UiText? = null,
    val errorMsgActionAdventure: UiText? = null,
    val errorMsgEducational: UiText? = null,
    val isLoadingSearch: Boolean = false,
    val isLoadingScienceFiction: Boolean = false,
    val isLoadingActionAdventure: Boolean = false,
    val isLoadingEducational: Boolean = false,
    val searchResult: List<AudioBook> = emptyList(),
    val scienceFictionBooks: List<AudioBook> = emptyList(),
    val actionAdventureBooks: List<AudioBook> = emptyList(),
    val educationalBooks: List<AudioBook> = emptyList()
)