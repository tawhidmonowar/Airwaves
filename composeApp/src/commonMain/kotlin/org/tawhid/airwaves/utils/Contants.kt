package org.tawhid.airwaves.utils

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.dark_mode
import airwaves.composeapp.generated.resources.light_mode
import airwaves.composeapp.generated.resources.system_default
import org.jetbrains.compose.resources.StringResource

enum class Theme(val title: StringResource) {
    SYSTEM_DEFAULT(Res.string.system_default),
    LIGHT_MODE(Res.string.light_mode),
    DARK_MODE(Res.string.dark_mode)
}
