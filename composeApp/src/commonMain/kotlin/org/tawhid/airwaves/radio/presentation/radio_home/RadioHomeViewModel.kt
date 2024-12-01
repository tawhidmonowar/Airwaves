package org.tawhid.airwaves.radio.presentation.radio_home

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
import org.tawhid.airwaves.core.domain.onError
import org.tawhid.airwaves.core.domain.onSuccess
import org.tawhid.airwaves.core.presentation.utils.toUiText
import org.tawhid.airwaves.radio.domain.Radio
import org.tawhid.airwaves.radio.domain.RadioRepository

class RadioHomeViewModel(
    private val radioRepository: RadioRepository
) : ViewModel() {

    private val cachedRadios = emptyList<Radio>()
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(RadioHomeState())
    val state = _state
        .onStart {
            if (cachedRadios.isEmpty()) {
                observeSearchQuery()
            }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: RadioHomeAction) {
        when (action) {
            is RadioHomeAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is RadioHomeAction.OnRadioClick -> {

            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMsgSearch = null,
                                searchResult = cachedRadios
                            )
                        }
                    }

                    query.length >= 3 -> {
                        searchJob?.cancel()
                        searchJob = searchRadios(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchRadios(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isSearchLoading = true
            )
        }
        radioRepository.searchRadios(query)
            .onSuccess { searchResult ->
                _state.update {
                    it.copy(
                        isSearchLoading = false,
                        errorMsgSearch = null,
                        searchResult = searchResult
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        searchResult = emptyList(),
                        isSearchLoading = false,
                        errorMsgSearch = error.toUiText()
                    )
                }
            }
    }
}