package org.tawhid.airwaves.book.openbook.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BookEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(BookDatabaseConstructor::class)
abstract class SaveBookDatabase: RoomDatabase() {
    abstract val saveBookDao: SaveBookDao

    companion object {
        const val OPEN_BOOK_DB_NAME = "open_book.db"
    }
}