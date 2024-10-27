package org.tawhid.airwaves.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.tawhid.airwaves.navigation.Graph
import org.tawhid.airwaves.navigation.MainScreen
import org.tawhid.airwaves.navigation.SettingRouteScreen
import org.tawhid.airwaves.presentations.settings.SettingScreen

@Composable
fun RootNavGraph() {
    val rootNavController = rememberNavController()
    NavHost(
        navController = rootNavController,
        route = Graph.RootScreenGraph,
        startDestination = Graph.MainScreenGraph
    ) {
        composable(route = Graph.MainScreenGraph) {
            MainScreen(rootNavController)
        }
        composable(route = SettingRouteScreen.Setting.route) {
            SettingScreen(rootNavController)
        }
    }
}