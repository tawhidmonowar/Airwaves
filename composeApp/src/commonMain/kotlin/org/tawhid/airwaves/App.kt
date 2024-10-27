package org.tawhid.airwaves

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tawhid.airwaves.navigation.graphs.RootNavGraph

@Composable
@Preview
fun App() {
    MaterialTheme {
        RootNavGraph()
    }
}