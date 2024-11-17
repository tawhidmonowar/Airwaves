package org.tawhid.airwaves.player

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class PlayerViewModel(
    private val repository: PlayerRepository
): ViewModel() {

    var isCollapsed by mutableStateOf(false)
        private set

    fun toggleCollapsed() {
        isCollapsed = !isCollapsed
    }


    var isPlaying by mutableStateOf(false)
        private set

    fun PlaySound(url : String) {
        repository.play(url)
        updatePlayingState()
    }

    fun updatePlayingState() {
        isPlaying = repository.isPlaying()
    }

    fun stop() {
        repository.stop()
        updatePlayingState()
    }

    @Composable
    fun playComp(url: String){
        repository.playComp(url)
        updatePlayingState()
    }


}