package com.example.news_compose_c40.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.example.news_compose_c40.R

@Composable
fun ErrorDialog(
    errorMessage: String,
    onRetry: (() -> Unit)?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },

        confirmButton = {
            if (onRetry!=null){
            TextButton(onClick = {
                onRetry()
            }) {
                Text(text = stringResource(id = R.string.try_again))
            }
        }
        }
                          ,dismissButton={
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }, title = {
            Text(text = stringResource(R.string.error))
        },
        text = {
            Text(text = errorMessage)
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    )
}

@Preview
@Composable
private fun ErrorDialogPreview() {
    ErrorDialog("This is an error",null,{})
}
