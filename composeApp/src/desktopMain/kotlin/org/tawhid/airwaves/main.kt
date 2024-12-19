package org.tawhid.airwaves

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.app_name
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.app.App
import org.tawhid.airwaves.core.di.initKoin
import org.tawhid.airwaves.core.theme.defaultWindowHeight
import org.tawhid.airwaves.core.theme.defaultWindowWidth
import org.tawhid.airwaves.core.theme.minWindowHeight
import org.tawhid.airwaves.core.theme.minWindowWidth
import java.awt.Dimension

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            state = WindowState(
                position = WindowPosition(Alignment.Center),
                size = DpSize(defaultWindowWidth, defaultWindowHeight),
            ),
            title = stringResource(Res.string.app_name),
        ) {
            window.minimumSize = Dimension(minWindowWidth, minWindowHeight)
            App()
        }
    }
}