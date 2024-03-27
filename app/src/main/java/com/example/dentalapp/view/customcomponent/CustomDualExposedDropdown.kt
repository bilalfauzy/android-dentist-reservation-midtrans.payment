package com.example.dentalapp.view.customcomponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomDualExposedDropdown(
    options1: List<String>,
    options2: List<String>,
    label1: String,
    label2: String,
    selectedOption1: String?,
    selectedOption2: String?,
    onOptionSelected1: (String) -> Unit,
    onOptionSelected2: (String) -> Unit
) {
    Column {
        Text(label1)
        DropdownMenu(
            expanded = false,
            onDismissRequest = { /* Dismiss the dropdown*/ },
        ) {
            options1.forEach { item ->
                DropdownMenuItem(onClick = {
                    onOptionSelected1(item)
                }) {
                    Text(item)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(label2)
        DropdownMenu(
            expanded = false,
            onDismissRequest = { /* Dismiss the dropdown*/ },
        ) {
            options2.forEach { item ->
                DropdownMenuItem(onClick = {
                    onOptionSelected2(item)
                }) {
                    Text(item)
                }
            }
        }
    }
}
