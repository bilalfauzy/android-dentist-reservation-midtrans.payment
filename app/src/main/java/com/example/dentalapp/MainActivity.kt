package com.example.dentalapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.toArgb
import com.example.dentalapp.routes.Navigation
import com.example.dentalapp.theme.DentalAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DentalAppTheme {
                window.statusBarColor = androidx.compose.material.MaterialTheme.colors.primary.toArgb()
                Navigation()
            }
        }
    }
}
