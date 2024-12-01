package org.tawhid.airwaves.book.openbook.presentation.book_home

import org.tawhid.airwaves.book.openbook.domain.Book
import org.tawhid.airwaves.core.presentation.utils.UiText

data class BookListState(
    val searchQuery: String = "",
    val searchResult: List<Book> = emptyList(),
    val trendingBooks: List<Book> = emptyList(),
    val savedBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMsg: UiText? = null
)
