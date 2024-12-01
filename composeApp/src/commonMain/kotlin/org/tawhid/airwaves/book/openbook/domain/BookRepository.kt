package org.tawhid.airwaves.book.openbook.domain

import kotlinx.coroutines.flow.Flow
import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.EmptyResult
import org.tawhid.airwaves.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getTrendingBooks(): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>
    suspend fun getBookSummary(prompt: String): Result<String?, DataError>
    suspend fun markAsSaved(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromSaved(id: String)
    fun getSavedBooks(): Flow<List<Book>>
    fun isBookSaved(id: String): Flow<Boolean>
}