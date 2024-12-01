package org.tawhid.airwaves.book.openbook.data.network

import org.tawhid.airwaves.book.openbook.data.dto.BookWorkDto
import org.tawhid.airwaves.book.openbook.data.dto.SearchResponseDto
import org.tawhid.airwaves.book.openbook.data.dto.TrendingResponseDto
import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result
import org.tawhid.airwaves.core.gemini.dto.GeminiResponseDto

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun fetchTrendingBooks(): Result<TrendingResponseDto, DataError.Remote>
    suspend fun fetchBookDescription(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
    suspend fun fetchBookSummary(prompt: String): Result<GeminiResponseDto, DataError.Remote>
}