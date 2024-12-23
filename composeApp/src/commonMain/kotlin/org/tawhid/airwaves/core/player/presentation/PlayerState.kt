package org.tawhid.airwaves.core.player.presentation

import org.tawhid.airwaves.core.player.domain.Player

data class PlayerState(
    val isLoading: Boolean = false,
    val isPlaying: Boolean = false,
    val isCollapsed: Boolean = false,
    val selectedPlayer: Player? = null,
)