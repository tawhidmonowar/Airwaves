package org.tawhid.airwaves.book.openbook.presentation.book_detail

import org.tawhid.airwaves.book.openbook.domain.Book

sealed interface BookDetailAction {
    data object OnBackClick : BookDetailAction
    data object OnSaveClick : BookDetailAction
    data class OnSelectedBookChange(val book: Book) : BookDetailAction
}