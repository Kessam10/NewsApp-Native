package com.example.news_compose_c40.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.news_compose_c40.ui.theme.green

@Composable
fun ProgressIndicator(isDisplayed: Boolean) {
    if (isDisplayed)
        Box(modifier = Modifier.fillMaxSize()) {
            androidx.compose.material3.CircularProgressIndicator(
                color = green,
                modifier = Modifier.align(Alignment.Center)
            )
        }

}

@Preview(showSystemUi = true)
@Composable
private fun PreviewProgressIndicator() {
    ProgressIndicator(true)
}
