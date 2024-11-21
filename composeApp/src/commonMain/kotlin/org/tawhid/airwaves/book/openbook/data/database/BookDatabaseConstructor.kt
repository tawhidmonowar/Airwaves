package org.tawhid.airwaves.book.openbook.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor: RoomDatabaseConstructor<SaveBookDatabase> {
    override fun initialize(): SaveBookDatabase
}