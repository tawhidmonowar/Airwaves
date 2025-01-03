package org.tawhid.airwaves.core.player.data.repository

import org.tawhid.airwaves.core.player.PlayerController
import org.tawhid.airwaves.core.player.domain.PlayerRepository

class PlayerRepositoryImpl(
    private val playerController: PlayerController
) : PlayerRepository {
    override fun play(audioUrl: String) {
        playerController.play(audioUrl)
    }

    override fun pauseResume() {
        playerController.pauseResume()
    }
}