package org.tawhid.airwaves.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.app.navigation.NavigationScreenRoot
import org.tawhid.airwaves.presentations.settings.SettingViewModel
import org.tawhid.airwaves.theme.AirwavesTheme

@Composable
fun App() {
    KoinContext {
        val settingViewModel = koinViewModel<SettingViewModel>()
        val currentTheme by settingViewModel.currentTheme.collectAsState()
        AirwavesTheme(currentTheme) {
            NavigationScreenRoot()
        }
    }
}