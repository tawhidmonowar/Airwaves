package org.tawhid.airwaves.book.openbook.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tawhid.airwaves.app.navigation.Route
import org.tawhid.airwaves.book.openbook.domain.BookRepository
import org.tawhid.airwaves.core.domain.onSuccess
import org.tawhid.airwaves.core.gemini.ApiPrompts.geminiBookSummaryPrompt

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id
    private val _state = MutableStateFlow(BookDetailState())

    val state = _state
        .onStart {
            fetchBookDescription()
            observeSavedStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(
                        book = action.book
                    )
                }
            }

            is BookDetailAction.OnSaveClick -> {
                viewModelScope.launch {
                    if (state.value.isSaved) {
                        bookRepository.deleteFromSaved(bookId)
                    } else {
                        state.value.book?.let { book ->
                            bookRepository.markAsSaved(book)
                        }
                    }
                }
            }

            is BookDetailAction.OnSummaryClick -> {
                getBookSummary()
            }

            else -> Unit
        }
    }

    private fun observeSavedStatus() {
        bookRepository.isBookSaved(bookId)
            .onEach { isSaved ->
                _state.update {
                    it.copy(
                        isSaved = isSaved
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository
                .getBookDescription(bookId)
                .onSuccess { description ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(
                                description = description
                            ),
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun getBookSummary() {
        viewModelScope.launch {
            _state.value.book?.let { book ->
                bookRepository.getBookSummary(prompt = geminiBookSummaryPrompt(book)).onSuccess { summary ->
                    _state.update {
                        it.copy(
                            summary = summary
                        )
                    }
                }
            }
        }
    }
}