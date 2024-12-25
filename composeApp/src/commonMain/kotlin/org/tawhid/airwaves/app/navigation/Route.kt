package org.tawhid.airwaves.app.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object RadioGraph : Route
    @Serializable
    data object Radio : Route
    @Serializable
    data class RadioViewMore(val type: String? = null): Route
    @Serializable
    data object RadioDetail : Route
    @Serializable
    data object PlayerToRadioDetail : Route

    @Serializable
    data object Home: Route
    @Serializable
    data object Podcast : Route
    @Serializable
    data object Book : Route
    @Serializable
    data object Setting : Route

    @Serializable
    data class BookDetail(val id: String? = null) : Route
    @Serializable
    data class AudioBookDetail(val id: String) : Route
}