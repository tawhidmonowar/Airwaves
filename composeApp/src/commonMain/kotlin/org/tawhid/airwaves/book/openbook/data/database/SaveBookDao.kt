package org.tawhid.airwaves.book.openbook.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveBookDao {

    @Upsert
    suspend fun upsert(book: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getSavedBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM BookEntity WHERE id = :id")
    suspend fun getSavedBook(id: String): BookEntity?

    @Query("DELETE FROM BookEntity WHERE id = :id")
    suspend fun deleteSavedBook(id: String)
}