package com.example.dentalapp.view.customcomponent

import androidx.compose.foundation.background
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.dentalapp.theme.backColor


@Composable
fun MyAppBar(
    title: String,
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = Modifier.background(backColor),
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = { onNavigationClick() }) {
                    Icon(navigationIcon, contentDescription = "Back")
                }
            }
        }
    )
}
