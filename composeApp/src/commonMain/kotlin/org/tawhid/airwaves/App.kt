package org.tawhid.airwaves

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tawhid.airwaves.navigation.graphs.RootNavGraph
import org.tawhid.airwaves.theme.AppTheme

import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    KoinContext {
        AppTheme("Dark Mode", false) {
            RootNavGraph()
        }
    }
}