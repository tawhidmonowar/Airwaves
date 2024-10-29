package org.tawhid.airwaves.presentations.radios

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.ic_retry
import airwaves.composeapp.generated.resources.no_radios_found
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.data.repository.radio.RadioRepository
import org.tawhid.airwaves.presentations.radios.components.EmptyContent
import org.tawhid.airwaves.presentations.radios.components.RadioListScreen
import org.tawhid.airwaves.presentations.radios.components.ShimmerEffect


@Composable
fun RadioScreen(
    navController: NavController
) {

    val radioScreenViewModel = viewModel {
        RadioScreenViewModel(RadioRepository())
    }

    val uiState by radioScreenViewModel.uiState.collectAsState()

    uiState.DisplayResult(
        onIdle = {},
        onLoading = {
            ShimmerEffect()
        },
        onSuccess = {
            if (it.isEmpty()) {
                EmptyContent(
                    message = stringResource(Res.string.no_radios_found),
                    icon = Res.drawable.ic_retry
                )
            } else {
                RadioListScreen(it, navController)
            }
        },
        onError = {
            EmptyContent(
                message = it,
                icon = Res.drawable.ic_retry,
                onRetryClick = {
                    radioScreenViewModel.getRadios()
                }
            )
        }
    )
}