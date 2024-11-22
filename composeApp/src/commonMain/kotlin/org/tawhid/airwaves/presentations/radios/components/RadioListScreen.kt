package org.tawhid.airwaves.presentations.radios.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.tawhid.airwaves.data.models.radio.RadioData
import org.tawhid.airwaves.theme.Shapes
import org.tawhid.airwaves.theme.cardMinSize
import org.tawhid.airwaves.theme.imageSize
import org.tawhid.airwaves.theme.largePadding
import org.tawhid.airwaves.theme.mediumPadding
import org.tawhid.airwaves.theme.onBackgroundDark
import org.tawhid.airwaves.theme.primaryContainerDark
import org.tawhid.airwaves.theme.primaryContainerLight
import org.tawhid.airwaves.theme.xSmallPadding
import org.tawhid.airwaves.theme.xxSmallPadding
import org.tawhid.airwaves.utils.DeviceType
import org.tawhid.airwaves.utils.getDeviceType
import org.tawhid.airwaves.utils.radioGenre

@Composable
fun RadioListScreen(
    radioData: List<RadioData>,
    navController: NavController
) {

    val isDesktop = remember {
        getDeviceType() == DeviceType.Desktop
    }

    Column(modifier = Modifier.fillMaxSize()) {

        radioGenreLazyRow()

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(cardMinSize),
            verticalItemSpacing = xxSmallPadding,
            modifier = Modifier.padding(mediumPadding),
            horizontalArrangement = Arrangement.spacedBy(xxSmallPadding),
        ) {
            item(span = StaggeredGridItemSpan.FullLine) {
                recentlyPlayed()
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                savedRadios()
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Text(text = "Verified Radios", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Browse All",  style = MaterialTheme.typography.titleMedium)
                }
            }

           /* items(radioData, key = { it.stationuuid }) { radio ->
                RadioThumbnail(radioData = radio, onClick = {
                    val radioDataStr = Json.encodeToString(radio)
                    navController.currentBackStackEntry?.savedStateHandle?.apply {
                        set("radio", radioDataStr)
                    }
                    navController.navigate(RadioDetailsScreenRoute.RadioDetails.route)
                })
            }*/
        }
    }
}


@Composable
fun radioGenreLazyRow() {

    val selectedIndex = remember { mutableStateOf(-1) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        state = listState,
        modifier = Modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        listState.scrollBy(-dragAmount)
                    }
                }
            }
    ) {
        items(radioGenre.size) { index ->
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = xSmallPadding, top = xxSmallPadding)
                    .clip(Shapes.small)
                    .clickable { selectedIndex.value = index }
                    .background(
                        color = if (selectedIndex.value == index) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.primary
                    )
                    .padding(xSmallPadding)
            ) {
                Text(
                    text = radioGenre[index].replaceFirstChar { it.uppercase() },
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Composable
fun recentlyPlayed() {

    val selectedIndex = remember { mutableStateOf(-1) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = Modifier.fillMaxWidth(),
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(text = "Recently Played", style = MaterialTheme.typography.titleMedium)

            Text(text = "View All", style = MaterialTheme.typography.titleMedium)

        }

        LazyRow(
            state = listState,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            listState.scrollBy(-dragAmount)
                        }
                    }
                }
        ) {

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(xSmallPadding)
                        .clip(Shapes.medium)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = mediumPadding, vertical = largePadding)
                        .fillMaxWidth()

                ) {

                    AsyncImage(
                        modifier = Modifier
                            .size(imageSize)
                            .clip(RoundedCornerShape(10))
                            .background(Color.White),
                        model = "https://icecast.walmradio.com:8443/christmas.png",
                        //error = painterResource(Res.drawable.logo),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )

                    Column(modifier = Modifier.padding(xSmallPadding)) {

                        Text(
                            text = "Recently Payed",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Text(
                            text = "You recently played this radio!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                    }
                }
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(xSmallPadding)
                        .clip(Shapes.medium)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = mediumPadding, vertical = largePadding)
                        .fillMaxWidth()

                ) {

                    AsyncImage(
                        modifier = Modifier
                            .size(imageSize)
                            .clip(RoundedCornerShape(10))
                            .background(Color.White),
                        model = "https://icecast.walmradio.com:8443/christmas.png",
                        //error = painterResource(Res.drawable.logo),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )

                    Column(modifier = Modifier.padding(xSmallPadding)) {

                        Text(
                            text = "Recently Payed",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Text(
                            text = "You recently played this radio!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun savedRadios() {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = Modifier.fillMaxWidth(),
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(text = "Saved Radios", style = MaterialTheme.typography.titleMedium)
            Text(text = "View All",  style = MaterialTheme.typography.titleMedium)
        }

        LazyRow(
            state = listState,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            listState.scrollBy(-dragAmount)
                        }
                    }
                }
        ) {

            item {
                Row(
                    modifier = Modifier
                        .padding(xSmallPadding)
                        .shadow(2.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(mediumPadding),
                    horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                        AsyncImage(
                            modifier = Modifier
                                .size(imageSize)
                                .clip(RoundedCornerShape(10))
                                .background(Color.White),
                            model = "https://icecast.walmradio.com:8443/christmas.png",
                            //error = painterResource(Res.drawable.logo),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )

                        Text(
                            text = "radioData.name",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )


                        Text(
                            text = "radioData.url",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(xSmallPadding)
                        .shadow(2.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(mediumPadding),
                    horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                        AsyncImage(
                            modifier = Modifier
                                .size(imageSize)
                                .clip(RoundedCornerShape(10))
                                .background(Color.White),
                            model = "https://icecast.walmradio.com:8443/christmas.png",
                            //error = painterResource(Res.drawable.logo),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )

                        Text(
                            text = "radioData.name",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )


                        Text(
                            text = "radioData.url",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(xSmallPadding)
                        .shadow(2.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(mediumPadding),
                    horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                        AsyncImage(
                            modifier = Modifier
                                .size(imageSize)
                                .clip(RoundedCornerShape(10))
                                .background(Color.White),
                            model = "https://icecast.walmradio.com:8443/christmas.png",
                            //error = painterResource(Res.drawable.logo),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )

                        Text(
                            text = "radioData.name",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )


                        Text(
                            text = "radioData.url",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(xSmallPadding)
                        .shadow(2.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(mediumPadding),
                    horizontalArrangement = Arrangement.spacedBy(mediumPadding),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                        AsyncImage(
                            modifier = Modifier
                                .size(imageSize)
                                .clip(RoundedCornerShape(10))
                                .background(Color.White),
                            model = "https://icecast.walmradio.com:8443/christmas.png",
                            //error = painterResource(Res.drawable.logo),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )

                        Text(
                            text = "radioData.name",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )


                        Text(
                            text = "radioData.url",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}