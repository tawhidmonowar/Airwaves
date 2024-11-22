package org.tawhid.airwaves.book.presentation

sealed interface BookHomeAction {
    data class OnTabSelected(val index: Int) : BookHomeAction
    data object OnSettingClick : BookHomeAction
}