package com.example.dentalapp.view.customcomponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        modifier = modifier,
        border = BorderStroke(1.dp, Color.White),
        elevation = 2.dp
    ) {
        content()
    }
}
