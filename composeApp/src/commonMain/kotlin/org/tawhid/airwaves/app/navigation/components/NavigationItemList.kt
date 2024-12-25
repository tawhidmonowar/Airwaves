package org.tawhid.airwaves.app.navigation.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.books
import airwaves.composeapp.generated.resources.home
import airwaves.composeapp.generated.resources.ic_book
import airwaves.composeapp.generated.resources.ic_home_filled
import airwaves.composeapp.generated.resources.ic_podcasts
import airwaves.composeapp.generated.resources.ic_radio_filled
import airwaves.composeapp.generated.resources.ic_settings_filled
import airwaves.composeapp.generated.resources.podcasts
import airwaves.composeapp.generated.resources.radios
import airwaves.composeapp.generated.resources.setting
import org.tawhid.airwaves.app.navigation.Route

val navigationItemsLists = listOf(
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_home_filled,
        selectedIcon = Res.drawable.ic_home_filled,
        title = Res.string.home,
        route = Route.Home
    ),
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_podcasts,
        selectedIcon = Res.drawable.ic_podcasts,
        title = Res.string.podcasts,
        route = Route.Podcast
    ),
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_book,
        selectedIcon = Res.drawable.ic_book,
        title = Res.string.books,
        route = Route.Book
    ),
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_radio_filled,
        selectedIcon = Res.drawable.ic_radio_filled,
        title = Res.string.radios,
        route = Route.RadioGraph
    )
)

val settingNavigationItems = NavigationItem(
    unSelectedIcon = Res.drawable.ic_settings_filled,
    selectedIcon = Res.drawable.ic_settings_filled,
    title = Res.string.setting,
    route = Route.Setting
)