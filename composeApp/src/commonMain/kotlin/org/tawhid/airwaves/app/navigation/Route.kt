package org.tawhid.airwaves.app.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object BookList : Route
    @Serializable
    data class BookDetail(val id: String) : Route
    @Serializable
    data object RadioDetail : Route
    @Serializable
    data object PlayerToRadioDetail : Route
    @Serializable
    data class AudioBookDetail(val id: String) : Route
}

object Graph {
    const val NAVIGATION_SCREEN_GRAPH = "navigationScreenGraph"
}

sealed class NavigationScreenRoute(var route: String) {
    data object Home : NavigationScreenRoute("home")
    data object Podcast : NavigationScreenRoute("podcast")
    data object Book : NavigationScreenRoute("book")
    data object Radio : NavigationScreenRoute("radio")
    data object Setting : NavigationScreenRoute("setting")
}