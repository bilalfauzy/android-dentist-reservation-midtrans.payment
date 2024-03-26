package com.example.dentalapp.view.customcomponent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dentalapp.theme.baseColor

@Composable
fun CustomDivider(){
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = baseColor,
        thickness = 1.dp
    )
}