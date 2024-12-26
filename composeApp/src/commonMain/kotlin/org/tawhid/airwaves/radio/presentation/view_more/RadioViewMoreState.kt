package org.tawhid.airwaves.radio.presentation.view_more

import org.tawhid.airwaves.core.utils.UiText
import org.tawhid.airwaves.radio.domain.Radio

data class RadioViewMoreState(
    val isLoading: Boolean = false,
    val radioList: List<Radio> = emptyList(),
    val errorMsg: UiText? = null,
    val isEndReached: Boolean = false,
    val currentPage: Int = 0,
    val currentViewMoreType: String? = null
)