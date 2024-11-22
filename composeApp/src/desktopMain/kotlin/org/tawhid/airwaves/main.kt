package org.tawhid.airwaves

import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import org.tawhid.airwaves.app.App
import org.tawhid.airwaves.di.initKoin
import java.awt.Dimension

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            state = WindowState(
                position = WindowPosition(Alignment.Center)
            ),
            title = "Airwaves",
        ) {
            window.minimumSize = Dimension(640, 480)
            App()
        }
    }
}