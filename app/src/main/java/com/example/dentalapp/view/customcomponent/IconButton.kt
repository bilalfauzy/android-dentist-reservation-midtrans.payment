package com.example.dentalapp.view.customcomponent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dentalapp.theme.baseColor

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    iconResId: Int,
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
                painter = painterResource(id = iconResId),
                contentDescription = description,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}