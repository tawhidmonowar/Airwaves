package org.tawhid.airwaves.core.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import org.tawhid.airwaves.book.openbook.data.database.SaveBookDatabase
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<SaveBookDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "Airwaves")
            os.contains("mac") -> File(userHome, "Library/Application Support/Airwaves")
            else -> File(userHome, ".local/share/Airwaves")
        }

        if(!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, SaveBookDatabase.OPEN_BOOK_DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}