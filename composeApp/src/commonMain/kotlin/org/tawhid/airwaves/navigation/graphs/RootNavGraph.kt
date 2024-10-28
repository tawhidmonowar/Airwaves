package org.tawhid.airwaves.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.tawhid.airwaves.navigation.Graph
import org.tawhid.airwaves.navigation.MainScreen
import org.tawhid.airwaves.navigation.RadioDetailsScreenRoute
import org.tawhid.airwaves.navigation.SettingScreenRoute
import org.tawhid.airwaves.presentations.radios.details.RadioDetailScreen
import org.tawhid.airwaves.presentations.settings.SettingScreen
import org.tawhid.airwaves.utils.radios

@Composable
fun RootNavGraph() {
    val rootNavController = rememberNavController()
    NavHost(
        navController = rootNavController,
        route = Graph.ROOT_SCREEN_GRAPH,
        startDestination = Graph.MAIN_SCREEN_GRAPH
    ) {
        composable(route = Graph.MAIN_SCREEN_GRAPH) {
            MainScreen(rootNavController)
        }
        composable(route = SettingScreenRoute.Setting.route) {
            SettingScreen(rootNavController)
        }
        composable(route = RadioDetailsScreenRoute.RadioDetails.route) {
            RadioDetailScreen(rootNavController, radios[0])
        }
    }
}