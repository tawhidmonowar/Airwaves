package org.tawhid.airwaves.core.presentation.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.search
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.resources.stringResource
import org.tawhid.airwaves.core.theme.DarkBlue
import org.tawhid.airwaves.core.theme.SandYellow
import org.tawhid.airwaves.core.theme.Shapes
import org.tawhid.airwaves.core.theme.xxSmallPadding

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EmbeddedSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onBack: () -> Unit,
    isActive: Boolean,
    content: @Composable () -> Unit,
    placeholder: String = stringResource(Res.string.search),
) {
    SearchBar(
        inputField = {
            EmbeddedSearchBarInputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                onBack = onBack,
                placeholder = placeholder
            )
        },
        expanded = isActive,
        onExpandedChange = { expanded ->
            if (!expanded) onBack()
        },
        colors = SearchBarDefaults.colors(
            dividerColor = Color.Transparent,
            containerColor = Color.Transparent
        ),
        content = {
            content()
        }
    )
}

@Composable
private fun EmbeddedSearchBarInputField(
    query: String,
    placeholder: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onBack: () -> Unit
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = DarkBlue,
            backgroundColor = SandYellow
        )
    ) {
        TextField(
            modifier = Modifier
                .padding(horizontal = xxSmallPadding),
            shape = Shapes.small,
            value = query,
            onValueChange = { onQueryChange(it) },
            singleLine = true,
            placeholder = {
                Text(text = placeholder)
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White
            ),
            leadingIcon = {
                IconButton(onClick = {
                    onBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.isNotBlank()
                ) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}