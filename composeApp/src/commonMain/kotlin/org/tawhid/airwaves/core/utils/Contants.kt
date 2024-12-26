package org.tawhid.airwaves.core.utils

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.dark_mode
import airwaves.composeapp.generated.resources.light_mode
import airwaves.composeapp.generated.resources.system_default
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import org.jetbrains.compose.resources.StringResource

enum class DeviceType {
    Mobile, Desktop
}

enum class Theme(val title: StringResource) {
    SYSTEM_DEFAULT(Res.string.system_default),
    LIGHT_MODE(Res.string.light_mode),
    DARK_MODE(Res.string.dark_mode)
}

sealed class WindowSize {
    data object Compact : WindowSize()
    data object Medium : WindowSize()
    data object Expanded : WindowSize()
}

object RadioViewMoreType {
    const val SAVED = "Saved"
    const val RECENTLY_PLAYED = "Recently Played"
    const val VERIFIED = "Verified"
    const val TRENDING = "Trending"
    const val RANDOM = "Random"
}

const val TUNING_DELAY = 500L


const val BASE_URL_RADIO = "https://de1.api.radio-browser.info/json/"

const val DATA_STORE_FILE_NAME = "setting.preferences_pb"

const val SEARCH_TRIGGER_CHAR = 3


val FadeIn = fadeIn(animationSpec = tween(220, delayMillis = 90)) +
        scaleIn(
            initialScale = 0.92f,
            animationSpec = tween(220, delayMillis = 90)
        )

val FadeOut = fadeOut(animationSpec = tween(90))


val radioGenre: List<String> = listOf(
    "pop",
    "music",
    "news",
    "rock",
    "classical",
    "talk",
    "islamic",
    "dance",
    "jazz",
    "country",
    "alternative",
    "folk",
    "information",
    "regional",
    "oldies",
    "sports",
    "electronic",
    "culture",
    "hiphop",
    "world music",
    "religious",
    "hits"
)