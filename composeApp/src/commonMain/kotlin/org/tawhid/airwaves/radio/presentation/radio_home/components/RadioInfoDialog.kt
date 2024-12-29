package org.tawhid.airwaves.radio.presentation.radio_home.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.apply
import airwaves.composeapp.generated.resources.cancel
import airwaves.composeapp.generated.resources.radio_disclaimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.core.theme.large
import org.tawhid.airwaves.core.theme.mediumPadding
import org.tawhid.airwaves.core.theme.xSmallPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioInfoDialog(
    onDismissRequest: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest, content = {
        Surface(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(mediumPadding)) {
                Text(
                    text = stringResource(Res.string.radio_disclaimer),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(xSmallPadding)
                )

                Spacer(modifier = Modifier.height(large))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(Res.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(mediumPadding))
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(Res.string.apply))
                    }
                }
            }
        }
    })
}