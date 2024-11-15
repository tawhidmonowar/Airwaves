package org.tawhid.airwaves

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.tawhid.airwaves.navigation.graphs.RootNavGraph
import org.tawhid.airwaves.theme.AirwavesTheme

import org.koin.compose.KoinContext
import org.tawhid.airwaves.presentations.settings.SettingViewModel

import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinContext {
        val settingViewModel = koinViewModel<SettingViewModel>()
        val currentTheme by settingViewModel.currentTheme.collectAsState()
        AirwavesTheme(currentTheme) {
            RootNavGraph(settingViewModel)
        }
    }
}