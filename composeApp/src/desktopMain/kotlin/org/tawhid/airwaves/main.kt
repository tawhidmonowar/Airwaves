package org.tawhid.airwaves

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import api.InsultCensorClient
import io.ktor.client.engine.okhttp.OkHttp
import org.tawhid.airwaves.data.api.createHttpClient

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Airwaves",
    ) {
        App(
            client = remember {
                InsultCensorClient(createHttpClient(OkHttp.create()))
            }
        )
    }
}