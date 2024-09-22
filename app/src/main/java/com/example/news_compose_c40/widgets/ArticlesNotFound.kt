package com.example.news_compose_c40.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news_compose_c40.R
import com.example.news_compose_c40.ui.theme.Poppins
import com.example.news_compose_c40.ui.theme.green


@Composable
fun ArticlesNotFound() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_results),
            contentDescription = stringResource(R.string.can_t_find_articles),
            colorFilter = ColorFilter.tint(
                green
            )
        )
        Text(
            text = stringResource(id = R.string.search_not_found),
            fontSize = 16.sp,
            color = Color.DarkGray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview
@Composable
private fun ArticlesNotFoundPreview() {
    ArticlesNotFound()
}
