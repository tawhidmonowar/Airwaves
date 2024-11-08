package org.tawhid.airwaves

import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.WindowState
import org.tawhid.airwaves.di.initKoin
import org.tawhid.airwaves.utils.createDataStore
import org.tawhid.airwaves.utils.DATA_STORE_FILE_NAME

fun main() {
    val prefs = createDataStore {
        DATA_STORE_FILE_NAME
    }
    application {
        initKoin()
        Window(
            onCloseRequest = ::exitApplication,
            state = WindowState(
                position = WindowPosition(Alignment.Center)
            ),
            title = "Airwaves",
        ) {
            App(prefs)
        }
    }
}