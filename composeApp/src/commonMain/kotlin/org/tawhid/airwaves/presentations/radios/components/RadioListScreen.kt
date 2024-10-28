package org.tawhid.airwaves.presentations.radios.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items

import androidx.compose.runtime.remember
import org.tawhid.airwaves.theme.cardMinSize
import org.tawhid.airwaves.theme.mediumPadding
import org.tawhid.airwaves.utils.DeviceType
import org.tawhid.airwaves.utils.getDeviceType
import org.tawhid.airwaves.utils.radios


@Composable
fun RadioListScreen() {

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
        items(radios, key = { it.changeuuid }) { radio ->
            RadioThumbnail(radioData = radio, onClick = {

            })
        }
    }
}

