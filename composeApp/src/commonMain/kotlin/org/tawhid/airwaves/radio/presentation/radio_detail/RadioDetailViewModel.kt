package org.tawhid.airwaves.radio.presentation.radio_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.tawhid.airwaves.radio.domain.RadioRepository

class RadioDetailViewModel(
    private val radioRepository: RadioRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RadioDetailState())
    val state = _state.onStart {

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onAction(action: RadioDetailAction) {
        when (action) {
            is RadioDetailAction.OnSelectedRadioChange -> {
                _state.update {
                    it.copy(
                        radio = action.radio
                    )
                }
            }

            RadioDetailAction.OnBackClick -> {}
            RadioDetailAction.OnSaveClick -> {}
        }
    }
}