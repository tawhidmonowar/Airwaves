package org.tawhid.airwaves.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.tawhid.airwaves.navigation.Graph
import org.tawhid.airwaves.navigation.MainRouteScreen
import org.tawhid.airwaves.presentations.books.BooksScreen
import org.tawhid.airwaves.presentations.home.HomeScreen
import org.tawhid.airwaves.presentations.podcasts.PodcastsScreen
import org.tawhid.airwaves.presentations.radios.RadiosScreen


@Composable
fun MainNavGraph(
    rootNavController : NavHostController,
    homeNavController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        navController = homeNavController,
        route = Graph.MainScreenGraph,
        startDestination = MainRouteScreen.Home.route
    )
    {
        composable(route = MainRouteScreen.Home.route) {
            HomeScreen()
        }

        composable(route = MainRouteScreen.Podcasts.route) {
            PodcastsScreen()
        }

        composable(route = MainRouteScreen.Radios.route) {
            RadiosScreen()
        }

        composable(route = MainRouteScreen.AudioBooks.route) {
            BooksScreen()
        }
    }
}