package org.tawhid.airwaves.presentations.radios.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.tawhid.airwaves.data.models.radio.RadioData
import org.tawhid.airwaves.player.PlayerViewModel
import org.tawhid.airwaves.theme.Shapes
import org.tawhid.airwaves.theme.imageSizeLarge
import org.tawhid.airwaves.theme.mediumPadding
import org.tawhid.airwaves.theme.xSmallPadding

@OptIn(
    ExperimentalMaterial3Api::class, KoinExperimentalAPI::class,
    ExperimentalFoundationApi::class
)
@Composable
fun RadioDetailScreen(
    navController: NavController,
    radioData: RadioData
) {
    val playerViewModel = koinViewModel<PlayerViewModel>()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(mediumPadding)
                    .size(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Hello World")
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    },
                    title = {
                        Text(
                            modifier = Modifier.basicMarquee(
                                delayMillis = 0,
                                initialDelayMillis = 0,
                                iterations = Int.MAX_VALUE
                            ),
                            text = radioData.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null,
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { contentPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
            ) {
                item {
                    RadioImage(radioData.favicon)
                }

                item {
                    radioDetail(radioData)
                }

                item {
                    ActionButtons()
                }

                item {
                    playerViewModel.playComp(radioData.url)
                }
            }

        }
    }
}

@Composable
fun radioDetail(currentRadio: RadioData) {

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(mediumPadding)
    ) {

        Spacer(modifier = Modifier.height(mediumPadding))

        Text(
            text = currentRadio.name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = currentRadio.country,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(mediumPadding))
    }
}


@Composable
fun RadioImage(imageUrl: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(mediumPadding)
    ) {
        Box(
            modifier = Modifier
                .size(imageSizeLarge)
                .clip(Shapes.medium)
                .background(Color.White)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ActionButtons() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(xSmallPadding) // Adds spacing between buttons
    ) {
        listOf("Save", "Lyrics", "Share", "Webpage").forEach { label ->
            item {
                Button(onClick = { /* TODO: Add respective actions */ }) {
                    Text(text = label)
                }
            }
        }
    }
}