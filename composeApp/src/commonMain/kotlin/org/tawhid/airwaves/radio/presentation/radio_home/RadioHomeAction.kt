package org.tawhid.airwaves.radio.presentation.radio_home

import org.tawhid.airwaves.radio.domain.Radio

sealed interface RadioHomeAction {
    data class OnSearchQueryChange(val query: String) : RadioHomeAction
    data class OnRadioClick(val radio: Radio) : RadioHomeAction
    data class OnViewMoreClick(val viewMoreType: String) : RadioHomeAction

    data object ActivateSearchMode : RadioHomeAction
    data object DeactivateSearchMode : RadioHomeAction

    data object OnSettingClick : RadioHomeAction
    data object OnBackClick : RadioHomeAction

    data object ShowRadioInfoDialog : RadioHomeAction
    data object HideRadioInfoDialog : RadioHomeAction

    data object LoadRadios : RadioHomeAction
    data object LoadVerifiedRadios : RadioHomeAction
}