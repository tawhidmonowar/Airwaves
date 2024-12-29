package org.tawhid.airwaves.core.presentation.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.map_location
import airwaves.composeapp.generated.resources.map_placeholder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.BuildKonfig
import org.tawhid.airwaves.core.theme.Shapes
import org.tawhid.airwaves.core.utils.GOOGLE_MAP_BASE_URL

@Composable
fun GoogleMapStaticImage(
    latitude: Double,
    longitude: Double,
    country: String? = null,
    state: String? = null,
    zoom: Int = 5,
    width: Int = 600,
    height: Int = 300,
    mapType: String = "roadmap",
    modifier: Modifier = Modifier
) {
    val imageUrl = if (latitude != 0.0 || longitude != 0.0) {
        GOOGLE_MAP_BASE_URL +
                "center=${latitude},${longitude}" +
                "&zoom=${zoom}" +
                "&size=${width}x${height}" +
                "&maptype=${mapType}" +
                "&markers=color:red|${latitude},${longitude}" +
                "&key=${BuildKonfig.GOOGLE_MAP_API_KEY}"
    } else if (country != null && state != null) {
        GOOGLE_MAP_BASE_URL +
                "center=${country},${state}" +
                "&zoom=${zoom}" +
                "&size=${width}x${height}" +
                "&maptype=${mapType}" +
                "&markers=color:red|${country}" +
                "&key=${BuildKonfig.GOOGLE_MAP_API_KEY}"
    } else {
        null
    }
    Box(
        modifier = modifier
            .height(222.dp)
            .clip(Shapes.small)
    ) {
        CustomAsyncImage(
            modifier = Modifier.fillMaxSize(),
            imageUrl = imageUrl,
            errorImage = painterResource(Res.drawable.map_placeholder),
            contentDescription = stringResource(Res.string.map_location)
        )
    }
}