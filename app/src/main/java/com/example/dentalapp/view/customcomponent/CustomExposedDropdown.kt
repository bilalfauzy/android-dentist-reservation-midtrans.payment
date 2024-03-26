package com.example.dentalapp.view.customcomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize


@Composable
fun CustomExposedDropdown(
    options: List<String>,
    label: String,
    errorText: String,
    onOptionSelected: (String) -> Unit,
    selectedOption: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(options.indexOf(selectedOption)) }
    var textFielSize by remember {
        mutableStateOf(Size.Zero)
    }

    Column{
        OutlinedTextField(
            value = selectedOption ?: "",
            onValueChange = {},
            label = {
                Text(text = label)
            },
            isError = errorText.isNotEmpty(),
            trailingIcon = {
                IconButton(
                    onClick = {
                        expanded = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            },
            singleLine = true,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    textFielSize = it.size.toSize()
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) {
                textFielSize.width.toDp()
            })
                .background(Color.White)
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        onOptionSelected(option)
                        expanded = false
                        errorText
                    },
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

