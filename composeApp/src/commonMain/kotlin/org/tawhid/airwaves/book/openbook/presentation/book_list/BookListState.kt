package org.tawhid.airwaves.book.openbook.presentation.book_list

import org.tawhid.airwaves.book.openbook.domain.Book
import org.tawhid.airwaves.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResult: List<Book> = emptyList(),
    val savedBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMsg: UiText? = null
)
