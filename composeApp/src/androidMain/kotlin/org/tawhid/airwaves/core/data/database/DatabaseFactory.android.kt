package org.tawhid.airwaves.core.data.database

import androidx.room.RoomDatabase

import android.content.Context
import androidx.room.Room
import org.tawhid.airwaves.book.openbook.data.database.SaveBookDatabase


actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<SaveBookDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(SaveBookDatabase.OPEN_BOOK_DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}