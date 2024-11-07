package org.tawhid.airwaves.player

import androidx.lifecycle.ViewModel

class PlayerViewModel(
    private val repository: PlayerRepository
): ViewModel() {

    fun getHelloWorldString() {
        repository.name()
    }

}