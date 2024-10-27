package org.tawhid.airwaves.navigation.data

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.audio_books
import airwaves.composeapp.generated.resources.home
import airwaves.composeapp.generated.resources.ic_book
import airwaves.composeapp.generated.resources.ic_home_filled
import airwaves.composeapp.generated.resources.ic_podcasts
import airwaves.composeapp.generated.resources.ic_radio_filled
import airwaves.composeapp.generated.resources.ic_radio_outlined
import airwaves.composeapp.generated.resources.podcasts
import airwaves.composeapp.generated.resources.radios
import org.tawhid.airwaves.navigation.MainRouteScreen

val BottomNavigationItemLists = listOf(

    BottomNavigationItem(
        icon = Res.drawable.ic_home_filled,
        title = Res.string.home,
        route = MainRouteScreen.Home.route
    ),
    BottomNavigationItem(
        icon = Res.drawable.ic_podcasts,
        title = Res.string.podcasts,
        route = MainRouteScreen.Podcasts.route,
    ),
    BottomNavigationItem(
        icon = Res.drawable.ic_radio_filled,
        title = Res.string.radios,
        route = MainRouteScreen.Radios.route
    ),
    BottomNavigationItem(
        icon = Res.drawable.ic_book,
        title = Res.string.audio_books,
        route = MainRouteScreen.AudioBooks.route
    )

)