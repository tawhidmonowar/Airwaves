package org.tawhid.airwaves.presentations.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.tawhid.airwaves.player.PlayerViewModel
import androidx.compose.runtime.*


@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<PlayerViewModel>()
    var name by remember { mutableStateOf("Play") }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button( onClick = {
            viewModel.getHelloWorldString()
        }) {
            Text(text = name)
        }
    }
}