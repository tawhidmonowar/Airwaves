package org.tawhid.airwaves.book.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookHomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(BookHomeState())
    val state = _state.asStateFlow()

    fun onAction(action: BookHomeAction) {
        when (action) {
            is BookHomeAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }
}