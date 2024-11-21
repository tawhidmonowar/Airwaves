package org.tawhid.airwaves.book.openbook.presentation.book_detail

import org.tawhid.airwaves.book.openbook.domain.Book


data class BookDetailState(
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val book: Book? = null
)