package org.tawhid.airwaves.radio.presentation.view_more

import org.tawhid.airwaves.radio.domain.Radio

sealed interface RadioViewMoreAction {
    data class OnRadioClick(val radio: Radio) : RadioViewMoreAction
    data class OnViewMoreTypeChange(val type: String) : RadioViewMoreAction
    data object OnBackClick : RadioViewMoreAction
    data object OnLoadMoreRadios : RadioViewMoreAction
}