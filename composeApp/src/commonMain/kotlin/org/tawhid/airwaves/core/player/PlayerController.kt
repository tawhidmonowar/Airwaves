package org.tawhid.airwaves.core.player

expect class PlayerController {
    fun play(audioUrl: String)
    fun pause()
}