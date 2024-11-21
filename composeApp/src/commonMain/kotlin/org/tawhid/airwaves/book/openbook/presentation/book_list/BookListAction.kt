package org.tawhid.airwaves.book.openbook.presentation.book_list

import org.tawhid.airwaves.book.openbook.domain.Book


sealed interface BookListAction {
    data class OnSearchQueryChange(val query: String) : BookListAction
    data class OnBookClick(val book: Book) : BookListAction
    data class OnTabSelected(val index: Int) : BookListAction
}