package org.tawhid.airwaves.core.presentation.setting

import org.tawhid.airwaves.utils.Theme

data class SettingState(
    val currentTheme: String? = Theme.SYSTEM_DEFAULT.name,
    val showClearDataDialog: Boolean = false,
    val showThemeDialog: Boolean = false
)