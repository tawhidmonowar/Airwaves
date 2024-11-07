package org.tawhid.airwaves

import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.WindowState
import org.tawhid.airwaves.di.initKoin


fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication, state = WindowState(
            position = WindowPosition(Alignment.Center)
        ),
        title = "Airwaves",
    ) {
        App()
    }
}