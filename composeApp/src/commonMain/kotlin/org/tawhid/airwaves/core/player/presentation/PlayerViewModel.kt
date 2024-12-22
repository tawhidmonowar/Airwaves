package org.tawhid.airwaves.core.player.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.tawhid.airwaves.core.player.domain.PlayerRepository
import org.tawhid.airwaves.radio.domain.Radio

class PlayerViewModel(
    private val repository: PlayerRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PlayerState())
    val state = _state.asStateFlow()

    fun onAction(action: PlayerAction) {
        when (action) {
            is PlayerAction.OnPlayClick -> {
                _state.update {
                    it.copy(
                        isPlaying = true
                    )
                }
                repository.play(action.url)
            }

            is PlayerAction.OnPauseClick -> {

            }
            is PlayerAction.OnCollapseClick -> {
                _state.update {
                    it.copy(isCollapsed = !it.isCollapsed)
                }
            }
        }
    }

    fun selectRadio(radio: Radio) {
        _state.update {
            it.copy(selectedRadio = radio)
        }
    }
}