package org.tawhid.airwaves.radio.presentation.radio_detail

import org.tawhid.airwaves.radio.domain.Radio

data class RadioDetailState(
    val isSaved: Boolean = false,
    val radio: Radio? = null
)