package org.tawhid.airwaves.core.data.database

import androidx.room.RoomDatabase
import org.tawhid.airwaves.book.openbook.data.database.SaveBookDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<SaveBookDatabase>
}