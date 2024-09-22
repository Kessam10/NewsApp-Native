package com.example.news_compose_c40.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news_compose_c40.R
import com.example.news_compose_c40.ui.theme.green
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(
    titleResourceId: Int? = null,
    titleString: String? = null,
    shouldDisplaySearchIcon: Boolean,
    shouldDisplayMenuIcon: Boolean,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onSearchClick: (() -> Unit)? = null
) {

    TopAppBar(
        title = {
            Text(
                text = if (titleResourceId != null) stringResource(titleResourceId) else titleString
                    ?: "",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = green),
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomEnd = 30.dp, bottomStart =
                30.dp
            )
        ),
        navigationIcon = {
            if (shouldDisplayMenuIcon)
                AppBarIconButton(R.drawable.ic_feather_menu, onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                })
            else
                Spacer(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(25.dp)
                )
        },
        actions = {

            if (shouldDisplaySearchIcon)
                AppBarIconButton(R.drawable.ic_search) {
                    if (onSearchClick != null) {
                        onSearchClick()
                    }
                }
            else
                Spacer(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(25.dp)
                )
        }
    )
}

@Composable
private fun AppBarIconButton(icon: Int, onClick: () -> Unit = {}) {
    Image(
        painter = painterResource(icon),
        contentDescription = stringResource(R.string.search),
        modifier = Modifier
            .padding(10.dp)
            .size(25.dp)
            .clickable(onClick = onClick)
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewNewsTopAppBar() {
    NewsTopAppBar(
        titleResourceId = R.string.app_name,
        shouldDisplaySearchIcon = true,
        shouldDisplayMenuIcon = true,
        scope = rememberCoroutineScope(),
        drawerState = rememberDrawerState(
            initialValue = DrawerValue.Closed
        )
    )

}
