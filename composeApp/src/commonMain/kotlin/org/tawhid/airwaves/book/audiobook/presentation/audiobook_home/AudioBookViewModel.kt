package org.tawhid.airwaves.book.audiobook.presentation.audiobook_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tawhid.airwaves.book.audiobook.domain.AudioBook
import org.tawhid.airwaves.book.audiobook.domain.AudioBookRepository
import org.tawhid.airwaves.core.domain.onError
import org.tawhid.airwaves.core.domain.onSuccess
import org.tawhid.airwaves.core.presentation.utils.toUiText
import org.tawhid.airwaves.utils.SEARCH_TRIGGER_CHAR

class AudioBookViewModel(
    private val audioBookRepository: AudioBookRepository
) : ViewModel() {

    private val cachedBooks = emptyList<AudioBook>()
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(AudioBookState())
    val state = _state.onStart {
        if (cachedBooks.isEmpty()) {
            observeSearchQuery()
        }
        getScienceFictionBooks()
        getActionAdventureBooks()
        getEducationalBooks()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    fun onAction(action: AudioBookAction) {
        when (action) {
            is AudioBookAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is AudioBookAction.OnAudioBookClick -> {}
            else -> Unit
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }.distinctUntilChanged().debounce(500L).onEach { query ->
            when {
                query.length < SEARCH_TRIGGER_CHAR -> {
                    _state.update {
                        it.copy(
                            errorMsgSearch = null,
                            searchResult = cachedBooks
                        )
                    }
                }

                query.length >= SEARCH_TRIGGER_CHAR -> {
                    searchJob?.cancel()
                    searchJob = searchAudioBooks(query)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun searchAudioBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoadingSearch = true
            )
        }
        audioBookRepository.searchAudioBooks(query).onSuccess { searchResult ->
            _state.update {
                it.copy(
                    isLoadingSearch = false,
                    errorMsgSearch = null,
                    searchResult = searchResult
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    searchResult = emptyList(),
                    isLoadingSearch = false,
                    errorMsgSearch = error.toUiText()
                )
            }
        }
    }

    private fun getScienceFictionBooks() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoadingScienceFiction = true
            )
        }
        audioBookRepository.getScienceFictionBooks().onSuccess { scienceFictionBooks ->
            _state.update {
                it.copy(
                    isLoadingScienceFiction = false,
                    errorMsgScienceFiction = null,
                    scienceFictionBooks = scienceFictionBooks
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    scienceFictionBooks = emptyList(),
                    isLoadingScienceFiction = false,
                    errorMsgScienceFiction = error.toUiText()
                )
            }
        }
    }

    private fun getActionAdventureBooks() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoadingActionAdventure = true
            )
        }
        audioBookRepository.getActionAdventureBooks().onSuccess { actionAdventureBooks ->
            _state.update {
                it.copy(
                    isLoadingActionAdventure = false,
                    errorMsgActionAdventure = null,
                    actionAdventureBooks = actionAdventureBooks
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    actionAdventureBooks = emptyList(),
                    isLoadingActionAdventure = false,
                    errorMsgActionAdventure = error.toUiText()
                )
            }
        }
    }

    private fun getEducationalBooks() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoadingEducational = true
            )
        }
        audioBookRepository.getEducationalBooks().onSuccess { educationalBooks ->
            _state.update {
                it.copy(
                    isLoadingEducational = false,
                    errorMsgEducational = null,
                    educationalBooks = educationalBooks
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    educationalBooks = emptyList(),
                    isLoadingEducational = false,
                    errorMsgEducational = error.toUiText()
                )
            }
        }
    }
}