package org.tawhid.airwaves.core.player.domain

interface PlayerRepository {
    fun play(audioUrl : String)
    fun pause()
}