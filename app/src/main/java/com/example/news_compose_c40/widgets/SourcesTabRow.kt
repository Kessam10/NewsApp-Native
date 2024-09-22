package com.example.news_compose_c40.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.news_compose_c40.model.source.Source
import com.example.news_compose_c40.ui.theme.green


@Composable
fun SourcesTabRow(sourcesList:List<Source>, onTabClicked:(String)->Unit) {

    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }


    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        divider = {},
        indicator = {},
        edgePadding = 8.dp
    ) {

        sourcesList.forEachIndexed { index, sourceItem ->

            Tab(
                selected = index == selectedTabIndex,
                onClick = {
                    selectedTabIndex = index
                    onTabClicked(sourceItem.id)
                },
                selectedContentColor = if (selectedTabIndex == index) Color.White else green,
                modifier =
                if (selectedTabIndex == index)
                    Modifier
                        .padding(5.dp)
                        .background(green, CircleShape)
                else Modifier
                    .padding(5.dp)
                    .border(width = 1.dp, green, shape = CircleShape)
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = sourceItem.name ?: "", modifier = Modifier.padding(8.dp))
            }
        }

    }

}
