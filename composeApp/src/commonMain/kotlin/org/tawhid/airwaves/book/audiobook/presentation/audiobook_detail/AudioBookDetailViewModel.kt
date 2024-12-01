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
import kotlinx.coroutines.launch
import org.tawhid.airwaves.app.navigation.Route
import org.tawhid.airwaves.book.audiobook.domain.AudioBookRepository
import org.tawhid.airwaves.core.domain.onError
import org.tawhid.airwaves.core.domain.onSuccess
import org.tawhid.airwaves.core.presentation.utils.toUiText

class AudioBookDetailViewModel(
    private val audioBookRepository: AudioBookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookId = savedStateHandle.toRoute<Route.AudioBookDetail>().id
    private val _state = MutableStateFlow(AudioBookDetailState())
    val state = _state
        .onStart {
            getAudioBookTracks()
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
        }
    }

    private fun getAudioBookTracks() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoadingAudioBookTracks = true
            )
        }
        audioBookRepository.getAudioBookTracks(bookId).onSuccess { audioBookTracks ->
            _state.update {
                it.copy(
                    isLoadingAudioBookTracks = false,
                    audioBookTracks = audioBookTracks
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    isLoadingAudioBookTracks = false,
                    errorAudioBookTracks = error.toUiText()
                )
            }
        }
    }
}