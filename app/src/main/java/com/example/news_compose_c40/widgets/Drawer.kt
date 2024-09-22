package com.example.news_compose_c40.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news_compose_c40.R
import com.example.news_compose_c40.ui.theme.green


@Composable
fun NavigationDrawerSheet(onNavigateToCategoriesClick:()->Unit,onNavigateToSettingsClick:()->Unit) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val isLandscape = screenWidthDp > screenHeightDp
    val drawerWidthPercent = if (isLandscape) 0.3f else 0.7f // Adjust the width based on orientation

    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(drawerWidthPercent),
        drawerContainerColor = Color.White
    ) {
        Column(horizontalAlignment = Alignment.Start) {


            Box(
                modifier = Modifier
                    .fillMaxHeight(.2f)
                    .fillMaxWidth()
                    .background(color = green), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            NavigationDrawerItem(R.drawable.ic_categories, stringResource(id = R.string.categories)){
                onNavigateToCategoriesClick()
            }
            Spacer(modifier = Modifier.height(10.dp))
            NavigationDrawerItem(R.drawable.ic_settings, stringResource(id = R.string.settings)){
                onNavigateToSettingsClick()
            }

        }
    }

}


@Composable
fun NavigationDrawerItem(icon: Int, label: String,onNavigationItemClick: ()->Unit) {

    Row(verticalAlignment = Alignment.CenterVertically,modifier= Modifier
        .fillMaxWidth()
        .clickable { onNavigationItemClick() }) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.padding(8.dp)
        )
        Text(text = label, modifier = Modifier.padding(start = 5.dp))
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewNavigationDrawerSheet() {
    NavigationDrawerSheet({},{})
}
