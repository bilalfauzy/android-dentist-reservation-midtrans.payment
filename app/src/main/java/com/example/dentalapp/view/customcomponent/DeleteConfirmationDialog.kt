package com.example.dentalapp.view.customcomponent

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmationDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menghapus?") },
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
