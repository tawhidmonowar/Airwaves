package org.tawhid.airwaves.radio.presentation.radio_home

import org.tawhid.airwaves.core.presentation.utils.UiText
import org.tawhid.airwaves.radio.domain.Radio

data class RadioHomeState(
    val searchQuery: String = "",
    val searchResult: List<Radio> = emptyList(),
    val isSearchLoading: Boolean = false,
    val searchErrorMsg: UiText? = null,

    val trendingRadios: List<Radio> = emptyList(),
    val isTrendingLoading: Boolean = false,
    val trendingErrorMsg: UiText? = null,

    val verifiedRadios: List<Radio> = emptyList(),
    val isVerifiedLoading: Boolean = false,
    val verifiedErrorMsg: UiText? = null,

    val latestRadios: List<Radio> = emptyList(),
    val isLatestLoading: Boolean = false,
    val latestErrorMsg: UiText? = null
)