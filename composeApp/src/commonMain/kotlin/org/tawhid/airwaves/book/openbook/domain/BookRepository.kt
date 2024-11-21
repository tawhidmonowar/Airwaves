package org.tawhid.airwaves.book.openbook.domain

import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.EmptyResult
import org.tawhid.airwaves.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>
    fun getSavedBooks(): Flow<List<Book>>
    fun isBookSaved(id: String): Flow<Boolean>
    suspend fun markAsSaved(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromSaved(id: String)
}