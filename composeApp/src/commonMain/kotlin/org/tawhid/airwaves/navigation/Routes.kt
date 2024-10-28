package org.tawhid.airwaves.navigation

object Graph {
    const val ROOT_SCREEN_GRAPH = "rootScreenGraph"
    const val MAIN_SCREEN_GRAPH = "mainScreenGraph"
}

sealed class MainScreenRoute(val route: String) {
    object Home : MainScreenRoute("home")
    object Podcasts : MainScreenRoute("podcasts")
    object Radios : MainScreenRoute("radios")
    object AudioBooks : MainScreenRoute("audioBooks")
}

sealed class SettingScreenRoute(val route: String) {
    object Setting : SettingScreenRoute("setting")
}

sealed class RadioDetailsScreenRoute(val route: String) {
    object RadioDetails : RadioDetailsScreenRoute("radioDetails")
}