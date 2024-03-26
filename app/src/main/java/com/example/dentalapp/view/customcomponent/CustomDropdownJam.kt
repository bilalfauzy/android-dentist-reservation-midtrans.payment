package com.example.dentalapp.view.customcomponent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomDropdownJam(
    options: List<String>,
    label: String,
    errorText: String,
    onOptionSelected: (String) -> Unit,
    selectedOption: String? = null,
    disabledItems: Set<String>
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
                val isItemDisabled = disabledItems.contains(option)
                DropdownMenuItem(
                    onClick = {
                        if (!isItemDisabled){
                            selectedIndex = index
                            onOptionSelected(option)
                            expanded = false
                            errorText
                        }
                    },
                    enabled = !isItemDisabled
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.body2,
                        color = if (isItemDisabled) Color.Gray else Color.Black
                    )
                }
            }
        }
    }
}