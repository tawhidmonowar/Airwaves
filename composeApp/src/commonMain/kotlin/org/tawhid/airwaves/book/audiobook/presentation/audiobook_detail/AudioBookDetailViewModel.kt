package org.tawhid.airwaves.book.audiobook.presentation.audiobook_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.tawhid.airwaves.app.navigation.Route

class AudioBookDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookId = savedStateHandle.toRoute<Route.AudioBookDetail>().id
    private val _state = MutableStateFlow(AudioBookDetailState())
    val state = _state
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: AudioBookDetailAction) {
        when (action) {
            is AudioBookDetailAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(
                        audioBook = action.audioBook
                    )
                }
            }
            else -> Unit
        }
    }
}