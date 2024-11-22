package org.tawhid.airwaves.presentations.radios

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.ic_retry
import airwaves.composeapp.generated.resources.ic_tune
import airwaves.composeapp.generated.resources.no_radios_found
import airwaves.composeapp.generated.resources.radios
import airwaves.composeapp.generated.resources.search_radios
import airwaves.composeapp.generated.resources.setting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.data.repository.radio.RadioRepository
import org.tawhid.airwaves.presentations.radios.components.EmptyContent
import org.tawhid.airwaves.presentations.radios.components.RadioListScreen
import org.tawhid.airwaves.presentations.radios.components.ShimmerEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioScreen(
    navController: NavController
) {
    val radioScreenViewModel = viewModel { RadioViewModel(RadioRepository()) }
    val uiState by radioScreenViewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val searchVisible = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                if (searchVisible.value) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        query = searchQuery.value,
                        onQueryChange = { searchQuery.value = it },
                        onSearch = {
                            searchVisible.value = false
                        },
                        active = searchVisible.value,
                        onActiveChange = { searchVisible.value = it },
                        placeholder = { Text(text = stringResource(Res.string.search_radios)) },
                        leadingIcon = {
                            IconButton(onClick = {
                                searchVisible.value = false
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                searchQuery.value = ""
                            }) {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_tune),
                                    contentDescription = stringResource(Res.string.radios)
                                )
                            }
                        }
                    ) {

                    }
                }
                /*TopAppBar(
                    title = {
                        Text(text = stringResource(Res.string.radios))
                    },
                    navigationIcon = {
                        IconButton(onClick = { TODO() }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = stringResource(Res.string.setting),
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { TODO() }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = stringResource(Res.string.setting),
                            )
                        }
                        IconButton(onClick = {
                            searchVisible.value = !searchVisible.value
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = stringResource(Res.string.setting),
                            )
                        }
                        IconButton(onClick = {
                            navController.navigate(SettingScreenRoute.Setting.route)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = stringResource(Res.string.setting),
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )*/
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
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
        }
    }
}