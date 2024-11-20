package org.tawhid.airwaves.app.navigation.components

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class NavigationItem(
    val unSelectedIcon: DrawableResource,
    val selectedIcon: DrawableResource,
    val title: StringResource,
    val route: String
)