package org.tawhid.airwaves.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.airwaves.app.navigation.components.CompactNavigationBar
import org.tawhid.airwaves.app.navigation.components.ExpandedNavigationBar
import org.tawhid.airwaves.app.navigation.components.MediumNavigationBar
import org.tawhid.airwaves.app.navigation.components.navigationItemsLists
import org.tawhid.airwaves.app.navigation.components.settingNavigationItems
import org.tawhid.airwaves.core.player.presentation.PlayingOverlay
import org.tawhid.airwaves.core.setting.SettingScreenRoot
import org.tawhid.airwaves.core.setting.SettingViewModel
import org.tawhid.airwaves.core.theme.expandedNavigationBarWidth
import org.tawhid.airwaves.core.theme.mediumNavigationBarWidth

@Composable
fun NavigationScreenRoot(
    settingViewModel: SettingViewModel = koinViewModel()
) {
    NavigationScreen(settingViewModel)
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun NavigationScreen(
    settingViewModel: SettingViewModel
) {
    val windowSizeClass = calculateWindowSizeClass()
    val isCompactScreen by remember(windowSizeClass) { derivedStateOf { windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact } }
    val isMediumScreen by remember(windowSizeClass) { derivedStateOf { windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium } }
    val isExpandedScreen by remember(windowSizeClass) { derivedStateOf { windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded } }

    val navigationItems = if (isExpandedScreen || isMediumScreen) {
        navigationItemsLists + settingNavigationItems
    } else {
        navigationItemsLists
    }

    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()

    val currentRoute by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry?.destination?.route
        }
    }

    val isNavigationBarsVisible by remember(currentRoute) {
        derivedStateOf {
            val commonRoutes = listOf(
                NavigationScreenRoute.Home.route,
                NavigationScreenRoute.Podcast.route,
                NavigationScreenRoute.Book.route,
                NavigationScreenRoute.Radio.route
            )
            if (isCompactScreen) {
                currentRoute in commonRoutes
            } else {
                currentRoute in commonRoutes + NavigationScreenRoute.Setting.route
            }
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isNavigationBarsVisible && isCompactScreen,
                enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight })
            ) {
                CompactNavigationBar(
                    items = navigationItems,
                    currentRoute = currentRoute,
                    onItemClick = { currentNavigationItem ->
                        rootNavController.navigate(currentNavigationItem.route) {
                            popUpTo(NavigationScreenRoute.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

        }
    ) { innerPadding ->

        val contentPadding = if (isExpandedScreen) {
            PaddingValues(start = expandedNavigationBarWidth)
        } else if (isMediumScreen) {
            PaddingValues(start = mediumNavigationBarWidth)
        } else {
            innerPadding
        }

        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = rootNavController,
                startDestination = Graph.NAVIGATION_SCREEN_GRAPH,
            ) {
                navGraph(rootNavController = rootNavController, innerPadding = contentPadding)
                composable(route = NavigationScreenRoute.Setting.route) {
                    SettingScreenRoot(
                        viewModel = settingViewModel,
                        innerPadding = contentPadding,
                        onBackClick = {
                            rootNavController.navigateUp()
                        }
                    )
                }
            }

            AnimatedVisibility(
                visible = isNavigationBarsVisible && isMediumScreen,
                enter = slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
            ) {
                MediumNavigationBar(
                    items = navigationItems,
                    currentRoute = currentRoute,
                    onItemClick = { currentNavigationItem ->
                        rootNavController.navigate(currentNavigationItem.route) {
                            popUpTo(NavigationScreenRoute.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            AnimatedVisibility(
                visible = isNavigationBarsVisible && isExpandedScreen,
                enter = slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
            ) {
                ExpandedNavigationBar(
                    items = navigationItems,
                    currentRoute = currentRoute,
                    onItemClick = { currentNavigationItem ->
                        rootNavController.navigate(currentNavigationItem.route) {
                            popUpTo(NavigationScreenRoute.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }


            AnimatedVisibility(
                visible = isNavigationBarsVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }),
                modifier = Modifier.align(Alignment.BottomCenter).padding(innerPadding)
            ) {
                PlayingOverlay(
                    onPlayerClick = {
                        rootNavController.navigate(Route.PlayerToRadioDetail)
                    }
                )
            }
        }
    }
}