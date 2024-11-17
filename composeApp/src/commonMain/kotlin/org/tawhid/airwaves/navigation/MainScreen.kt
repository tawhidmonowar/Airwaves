package org.tawhid.airwaves.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.core.annotation.KoinExperimentalAPI
import org.tawhid.airwaves.navigation.data.BottomNavigationItemList
import org.tawhid.airwaves.navigation.graphs.MainNavGraph
import org.tawhid.airwaves.player.PlayingOverlay


@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun MainScreen(rootNavController: NavHostController) {

    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()

    val currentRoute by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry?.destination?.route
        }
    }

    Scaffold(
        bottomBar = {
            Text(text = "Bottom Bar")
            BottomNavigationBar(
                bottomNavigationItems = BottomNavigationItemList,
                currentRoute = currentRoute,
                onItemClick = { currentBottomNavigationItem ->

                    homeNavController.navigate(currentBottomNavigationItem.route) {
                        homeNavController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true

                    }
                }
            )
        }
    ) { innerPadding ->
        MainNavGraph(rootNavController, homeNavController, innerPadding)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PlayingOverlay(
                isPlaying = true,
                onButtonClick = {
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(1f)
                    .clip(shape = MaterialTheme.shapes.medium)
            )
        }
    }
}
