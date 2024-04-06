package com.example.dentalapp.view.customcomponent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    iconResId: ImageVector,
    description: String,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        backgroundColor = color
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = iconResId,
                contentDescription = description,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}