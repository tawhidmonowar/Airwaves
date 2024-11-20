package org.tawhid.airwaves.app.navigation.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.books
import airwaves.composeapp.generated.resources.home
import airwaves.composeapp.generated.resources.ic_book
import airwaves.composeapp.generated.resources.ic_home_filled
import airwaves.composeapp.generated.resources.ic_podcasts
import airwaves.composeapp.generated.resources.ic_radio_filled
import airwaves.composeapp.generated.resources.podcasts
import airwaves.composeapp.generated.resources.radios
import org.tawhid.airwaves.app.navigation.NavigationScreenRoute

val navigationItemsLists = listOf(
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_home_filled,
        selectedIcon = Res.drawable.ic_home_filled,
        title = Res.string.home,
        route = NavigationScreenRoute.Home.route,
    ),
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_podcasts,
        selectedIcon = Res.drawable.ic_podcasts,
        title = Res.string.podcasts,
        route = NavigationScreenRoute.Podcast.route,
    ),
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_book,
        selectedIcon = Res.drawable.ic_book,
        title = Res.string.books,
        route = NavigationScreenRoute.Book.route,
    ),
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_radio_filled,
        selectedIcon = Res.drawable.ic_radio_filled,
        title = Res.string.radios,
        route = NavigationScreenRoute.Radio.route,
    )
)