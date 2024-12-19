package org.tawhid.airwaves.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.app.navigation.NavigationScreenRoot
import org.tawhid.airwaves.core.setting.SettingViewModel
import org.tawhid.airwaves.core.theme.AirwavesTheme

@Composable
fun App() {
    KoinContext {
        val settingViewModel = koinViewModel<SettingViewModel>()
        val settingState by settingViewModel.state.collectAsState()
        val currentTheme = settingState.currentTheme
        AirwavesTheme(currentTheme) {
            NavigationScreenRoot(settingViewModel)
        }
    }
}