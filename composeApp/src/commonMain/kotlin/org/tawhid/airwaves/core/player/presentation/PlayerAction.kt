package org.tawhid.airwaves.core.player.presentation

sealed interface PlayerAction {
    data class OnPlayClick(val url: String) : PlayerAction
    data object OnPauseClick : PlayerAction
    data object OnCollapseClick : PlayerAction
}