package org.tawhid.airwaves.navigation.data

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.books
import airwaves.composeapp.generated.resources.home
import airwaves.composeapp.generated.resources.ic_book
import airwaves.composeapp.generated.resources.ic_home_filled
import airwaves.composeapp.generated.resources.ic_podcasts
import airwaves.composeapp.generated.resources.ic_radio_filled
import airwaves.composeapp.generated.resources.ic_sound_room
import airwaves.composeapp.generated.resources.podcasts
import airwaves.composeapp.generated.resources.radios
import org.tawhid.airwaves.navigation.MainScreenRoute

val BottomNavigationItemList = listOf(

    BottomNavigationItem(
        icon = Res.drawable.ic_home_filled,
        title = Res.string.home,
        route = MainScreenRoute.Home.route
    ),
    BottomNavigationItem(
        icon = Res.drawable.ic_podcasts,
        title = Res.string.podcasts,
        route = MainScreenRoute.Podcasts.route,
    ),
    BottomNavigationItem(
        icon = Res.drawable.ic_book,
        title = Res.string.books,
        route = MainScreenRoute.AudioBooks.route
    ),
    BottomNavigationItem(
        icon = Res.drawable.ic_radio_filled,
        title = Res.string.radios,
        route = MainScreenRoute.Radios.route
    )

)