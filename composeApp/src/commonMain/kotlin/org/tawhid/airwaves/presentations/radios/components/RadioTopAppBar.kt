package org.tawhid.airwaves.presentations.radios.components

import airwaves.composeapp.generated.resources.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.navigation.SettingScreenRoute
import org.tawhid.airwaves.theme.xSmallPadding

@Composable
fun RadioTopAppBar(
    navController: NavController
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .clip(MaterialTheme.shapes.small)
            .padding(xSmallPadding)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = xSmallPadding)
        ) {
            Text(
                text = stringResource(Res.string.online_radio_stations),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        IconButton(onClick = {
            navController.navigate(SettingScreenRoute.Setting.route)
        }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = stringResource(Res.string.setting),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

    }
}
