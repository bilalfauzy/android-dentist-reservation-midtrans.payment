package com.example.dentalapp.view.customcomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun CircleShape(
    modifier: Modifier
){
    Box(){
        Box(modifier = modifier)
    }
}