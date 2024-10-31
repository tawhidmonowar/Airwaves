package org.tawhid.airwaves.presentations.radios.details

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.radios
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import org.tawhid.airwaves.player.AudioPlayer

import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.data.models.radio.RadioData
import org.tawhid.airwaves.theme.mediumPadding
import org.tawhid.airwaves.theme.xLargePadding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioDetailScreen(
    navController: NavController,
    currentRadio: RadioData
) {
    Scaffold(
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
                        text = stringResource(Res.string.radios),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
            )
        },
    ){ innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(mediumPadding),
            contentPadding = PaddingValues(horizontal = xLargePadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(mediumPadding)
        ){
            item {
                Text(text = currentRadio.name)
                Text(text = currentRadio.url)
            }
            item {
                AudioPlayer (
                    modifier = Modifier.fillMaxWidth(),
                    url = currentRadio.url,
                    startTime = Color.Black,
                    endTime = Color.Black,
                    volumeIconColor = Color.Black,
                    playIconColor = Color.Blue,
                    sliderTrackColor = Color.LightGray,
                    sliderIndicatorColor = Color.Blue
                )
            }
        }
    }
}

