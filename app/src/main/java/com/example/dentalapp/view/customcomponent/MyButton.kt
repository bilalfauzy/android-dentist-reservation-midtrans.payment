package com.example.dentalapp.view.customcomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun MyButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = Modifier.fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp)),
        onClick = onClick
    ) {
        Text(
            text = text
        )
    }
}