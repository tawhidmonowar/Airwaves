package org.tawhid.airwaves.app.navigation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CompactNavigationBar(
    items: List<NavigationItem>,
    currentRoute: String?,
    onItemClick: (NavigationItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items.forEach { navigationItem ->
            val isSelected = currentRoute == navigationItem.route
            val tabScale = animateFloatAsState(targetValue = if (isSelected) 1.1f else 1f)
            NavigationBarItem(
                modifier = Modifier.scale(tabScale.value),
                selected = currentRoute == navigationItem.route,
                onClick = { onItemClick(navigationItem) },
                icon = {
                    Icon(
                        painter = if (navigationItem.route == currentRoute) painterResource(
                            navigationItem.selectedIcon
                        ) else painterResource(navigationItem.unSelectedIcon),
                        contentDescription = stringResource(navigationItem.title),
                    )
                },
                label = {
                    Text(
                        text = stringResource(navigationItem.title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}