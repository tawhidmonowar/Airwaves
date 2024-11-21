package org.tawhid.airwaves.book.openbook.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tawhid.airwaves.book.openbook.data.database.SaveBookDao
import org.tawhid.airwaves.book.openbook.data.mappers.toBook
import org.tawhid.airwaves.book.openbook.data.mappers.toBookEntity
import org.tawhid.airwaves.book.openbook.data.network.RemoteBookDataSource
import org.tawhid.airwaves.book.openbook.domain.Book
import org.tawhid.airwaves.book.openbook.domain.BookRepository
import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.EmptyResult
import org.tawhid.airwaves.core.domain.Result
import org.tawhid.airwaves.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val saveBookDao: SaveBookDao
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        val localResult = saveBookDao.getSavedBook(bookId)
        return if (localResult==null) {
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getSavedBooks(): Flow<List<Book>> {
        return saveBookDao.getSavedBooks().map { bookEntities ->
            bookEntities.map {
                it.toBook()
            }
        }
    }

    override fun isBookSaved(id: String): Flow<Boolean> {
        return saveBookDao.getSavedBooks().map { bookEntities ->
            bookEntities.any { id == it.id }
        }
    }

    override suspend fun markAsSaved(book: Book): EmptyResult<DataError.Local> {
        return try {
            saveBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromSaved(id: String) {
        saveBookDao.deleteSavedBook(id)
    }
}