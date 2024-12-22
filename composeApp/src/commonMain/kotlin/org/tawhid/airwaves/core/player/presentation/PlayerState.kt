package org.tawhid.airwaves.core.player.presentation

import org.tawhid.airwaves.radio.domain.Radio

data class PlayerState(
    val isLoading: Boolean = false,
    val isPlaying: Boolean = false,
    val isCollapsed: Boolean = false,
    val selectedRadio: Radio? = null,
)