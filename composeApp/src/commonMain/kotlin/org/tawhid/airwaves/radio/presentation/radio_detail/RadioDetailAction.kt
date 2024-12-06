package org.tawhid.airwaves.radio.presentation.radio_detail

import org.tawhid.airwaves.radio.domain.Radio

sealed interface RadioDetailAction {
    data object OnBackClick : RadioDetailAction
    data object OnSaveClick : RadioDetailAction
    data class OnSelectedRadioChange(val radio: Radio) : RadioDetailAction
}