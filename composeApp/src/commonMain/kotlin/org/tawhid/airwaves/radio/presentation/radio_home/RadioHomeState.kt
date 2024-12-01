package org.tawhid.airwaves.radio.presentation.radio_home

import org.tawhid.airwaves.core.presentation.utils.UiText
import org.tawhid.airwaves.radio.domain.Radio

data class RadioHomeState(
    val searchQuery: String = "",
    val searchResult: List<Radio> = emptyList(),
    val isSearchLoading: Boolean = false,
    val errorMsgSearch: UiText? = null
)