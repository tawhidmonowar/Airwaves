package org.tawhid.airwaves.radio.presentation.view_more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tawhid.airwaves.core.domain.onError
import org.tawhid.airwaves.core.domain.onSuccess
import org.tawhid.airwaves.core.utils.TUNING_DELAY
import org.tawhid.airwaves.core.utils.toUiText
import org.tawhid.airwaves.radio.domain.RadioRepository

class RadioViewMoreViewModel(
    private val radioRepository: RadioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RadioViewMoreState())
    val state = _state.onStart {
        loadRadios(page = _state.value.currentPage)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onAction(action: RadioViewMoreAction) {
        when (action) {
            is RadioViewMoreAction.OnLoadMoreRadios -> {
                loadRadios(page = state.value.currentPage)
            }
            is RadioViewMoreAction.OnViewMoreTypeChange -> {
                _state.update {
                    it.copy(
                        currentViewMoreType = action.type
                    )
                }
            }
            else -> Unit
        }
    }

    private fun loadRadios(page: Int) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        radioRepository.getVerifiedRadios(page = page).onSuccess { radios ->
            delay(TUNING_DELAY)
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMsg = null,
                    radioList = it.radioList + radios,
                    isEndReached = radios.isEmpty(),
                    currentPage = page + 1
                )
            }

        }.onError { error ->
            delay(TUNING_DELAY)
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMsg = error.toUiText(),
                )
            }
        }
    }
}