package org.tawhid.airwaves

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tawhid.airwaves.navigation.graphs.RootNavGraph
import org.tawhid.airwaves.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme( "Dark Mode", true) {
        RootNavGraph()
    }
}