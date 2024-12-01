package org.tawhid.airwaves.radio.presentation.radio_home

import org.tawhid.airwaves.radio.domain.Radio

sealed interface RadioHomeAction {
    data class OnSearchQueryChange(val query: String) : RadioHomeAction
    data class OnRadioClick(val radio: Radio) : RadioHomeAction
}