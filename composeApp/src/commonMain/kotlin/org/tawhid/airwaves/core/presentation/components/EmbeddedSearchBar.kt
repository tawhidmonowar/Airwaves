package org.tawhid.airwaves.core.presentation.components

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.search
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import org.jetbrains.compose.resources.stringResource
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
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(isActive) {
        if (isActive) {
            focusRequester.requestFocus()
        }
    }
    SearchBar(
        inputField = {
            EmbeddedSearchBarInputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                onBack = onBack,
                placeholder = placeholder,
                focusRequester = focusRequester
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
    onBack: () -> Unit,
    focusRequester: FocusRequester
) {
    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = query,
                selection = TextRange(query.length)
            )
        )
    }
    TextField(
        modifier = Modifier
            .padding(horizontal = xxSmallPadding)
            .focusRequester(focusRequester),
        shape = Shapes.small,
        value = TextFieldValue(
            text = query,
            selection = TextRange(query.length)
        ),
        onValueChange = {
            onQueryChange(it.text)
        },
        singleLine = true,
        placeholder = {
            Text(text = placeholder)
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.White,
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