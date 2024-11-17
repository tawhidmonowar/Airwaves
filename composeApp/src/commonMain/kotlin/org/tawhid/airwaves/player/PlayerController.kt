package org.tawhid.airwaves.player

import androidx.compose.runtime.Composable

expect class PlayerController {

    fun prepare(pathSource: String)

    @Composable
    fun playCompose(audioUrl: String)

    fun start()

    fun pause()

    fun stop()

    fun isPlaying(): Boolean

    fun release()
}