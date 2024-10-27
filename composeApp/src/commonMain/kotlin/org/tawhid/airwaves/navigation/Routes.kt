package org.tawhid.airwaves.navigation

object Graph {
    const val RootScreenGraph = "rootScreenGraph"
    const val MainScreenGraph = "mainScreenGraph"
}

sealed class MainRouteScreen(val route: String) {
    object Home : MainRouteScreen("home")
    object Podcasts : MainRouteScreen("podcasts")
    object Radios : MainRouteScreen("radios")
    object AudioBooks : MainRouteScreen("audioBooks")
}

sealed class SettingRouteScreen(val route: String) {
    object Setting : SettingRouteScreen("setting")
}