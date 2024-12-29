package org.tawhid.airwaves.radio.presentation.radio_home

import org.tawhid.airwaves.core.utils.UiText
import org.tawhid.airwaves.radio.domain.Radio

data class RadioHomeState(
    val searchQuery: String = "",
    val searchResult: List<Radio> = emptyList(),
    val isSearchActive: Boolean = false,
    val isSearchLoading: Boolean = false,
    val searchErrorMsg: UiText? = null,

    val verifiedRadios: List<Radio> = emptyList(),
    val isVerifiedLoading: Boolean = false,
    val verifiedErrorMsg: UiText? = null,

    val radios: List<Radio> = emptyList(),
    val isLoading: Boolean = false,
    val errorMsg: UiText? = null,
    val endReached: Boolean = false,
    val page: Int = 0,

    val viewMoreType: String? = null,
    val savedRadios: List<Radio> = emptyList(),

    val showRadioInfoDialog: Boolean = false,
)