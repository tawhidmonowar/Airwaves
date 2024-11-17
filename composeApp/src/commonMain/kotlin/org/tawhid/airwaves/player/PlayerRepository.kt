package org.tawhid.airwaves.player

import androidx.compose.runtime.Composable


interface PlayerRepository {
    fun helloWorld(): String
    fun play(url : String)
    fun stop()
    fun isPlaying() : Boolean
    @Composable
    fun playComp(url : String)

}

class PlayerRepositoryImpl(
    private val playerController: PlayerController

) : PlayerRepository {
    override fun helloWorld(): String {
        return "Hello World!"
    }

    override fun play(url: String) {
        playerController.prepare(url)
    }

    override fun stop() {
        playerController.stop()
    }

    override fun isPlaying() : Boolean {
        return playerController.isPlaying()
    }
    @Composable
    override fun playComp(url: String) {
        return playerController.playCompose(url)
    }
}