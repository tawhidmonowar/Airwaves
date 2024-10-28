package org.tawhid.airwaves.presentations.radios.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import kotlinx.serialization.json.Json
import org.tawhid.airwaves.data.models.radio.RadioData
import org.tawhid.airwaves.navigation.RadioDetailsScreenRoute
import org.tawhid.airwaves.theme.cardMinSize
import org.tawhid.airwaves.theme.mediumPadding
import org.tawhid.airwaves.utils.DeviceType
import org.tawhid.airwaves.utils.getDeviceType

import kotlinx.serialization.encodeToString




@Composable
fun RadioListScreen(
    radioData: List<RadioData>,
    navController: NavController
) {

    val isDesktop = remember {
        getDeviceType() == DeviceType.Desktop
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(cardMinSize),
        verticalItemSpacing = mediumPadding,
        horizontalArrangement = Arrangement.spacedBy(mediumPadding),
        contentPadding = PaddingValues(mediumPadding)
        ,
    ) {
        items(radioData, key = { it.stationuuid }) { radio ->
            RadioThumbnail(radioData = radio, onClick = {
                val radioDataStr = Json.encodeToString(radio)
                navController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("radio", radioDataStr)
                }
                navController.navigate(RadioDetailsScreenRoute.RadioDetails.route)
            })
        }
    }
}

