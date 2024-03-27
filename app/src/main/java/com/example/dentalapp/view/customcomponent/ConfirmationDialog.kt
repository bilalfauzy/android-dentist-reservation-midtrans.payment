package com.example.dentalapp.view.customcomponent

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    alertText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Konfirmasi") },
            text = { Text(alertText) },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    onDismiss()
                }) {
                    Text("YA")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("TIDAK")
                }
            }
        )
    }
}
