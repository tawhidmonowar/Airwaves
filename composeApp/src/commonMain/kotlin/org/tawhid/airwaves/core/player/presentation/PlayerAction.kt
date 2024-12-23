package org.tawhid.airwaves.core.player.presentation

import org.tawhid.airwaves.core.player.domain.Player

sealed interface PlayerAction {
    data class OnPlayClick(val audioUrl: String) : PlayerAction
    data class OnSelectPlayer(val player: Player) : PlayerAction
    data object OnPauseClick : PlayerAction
    data object OnCollapseClick : PlayerAction
}