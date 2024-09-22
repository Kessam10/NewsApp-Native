package com.example.news_compose_c40.screens.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import kotlinx.serialization.Serializable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.ui.unit.dp


@Serializable
object SearchRoute
@Composable
fun SearchScreen(
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    onClose: () -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = { query ->
            onQueryChanged(query) // Updates the query in ViewModel
        },
        placeholder = { Text(text = "Search news...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        trailingIcon = {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close search")
            }
        }
    )
}



