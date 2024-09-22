package com.example.news_compose_c40.screens.news_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news_compose_c40.R
import com.example.news_compose_c40.activity.HomeActivity
import com.example.news_compose_c40.model.article.Article
import com.example.news_compose_c40.model.source.Source
import com.example.news_compose_c40.screens.news.getErrorMessage
import com.example.news_compose_c40.ui.theme.Poppins
import com.example.news_compose_c40.ui.theme.textColor
import com.example.news_compose_c40.widgets.ErrorDialog
import com.example.news_compose_c40.widgets.NewsCard
import com.example.news_compose_c40.widgets.NewsTopAppBar
import com.example.news_compose_c40.widgets.ProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable

@Serializable
data class NewsDetailsRoute(val title:String,val sourceName:String)

@Composable
fun NewsDetailsScreen(vm:NewsDetailsViewModel= hiltViewModel(),sourceName:String, scope: CoroutineScope, drawerState: DrawerState) {

    LaunchedEffect(key1 = null) {
        vm.getArticle()
    }

    val foundError = vm.uiMessage.errorMessage != null || vm.uiMessage.errorMessageId != null
    if (foundError&&vm.isErrorDialogVisible) {

        var errorMessage: String = getErrorMessage(vm.uiMessage.errorMessage, vm.uiMessage.errorMessageId)

        if (vm.isErrorDialogVisible) {
            ErrorDialog(
                errorMessage,
                onRetry = vm.uiMessage.retryAction,
                onDismiss = { vm.hideErrorDialog() }
            )
        }
    }
    
    Scaffold (topBar = {NewsTopAppBar(
        shouldDisplaySearchIcon = false,
        shouldDisplayMenuIcon = false,
        scope = scope,
        drawerState = drawerState 
    )}){padding->
     
        ProgressIndicator(isDisplayed = vm.uiMessage.isLoading)

        vm.article?.let {
            NewsDetailsContent(Modifier.padding(top = padding.calculateTopPadding()),it)
        }
    }
}

@Composable
fun NewsDetailsContent(modifier: Modifier, article: Article) {
    Column(modifier = modifier
        .paint(painterResource(id = R.drawable.bg_pattern), contentScale = ContentScale.Crop)
        .verticalScroll(
            rememberScrollState()
        )) {
        NewsCard(article = article)
        NewsDetailsCard(article)

    }
}
@Composable
fun NewsDetailsCard(article: Article) {
    val context = (LocalContext.current) as HomeActivity

    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        Text(
            text = article.content ?: "",
            modifier = Modifier.padding(10.dp),
            fontFamily = Poppins,
            color = textColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                context.openWebsiteForNews(article.url)
            },
            verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.fillMaxWidth(.5f))
            Text(
                text = stringResource(R.string.view_full_article),
                modifier = Modifier.padding(8.dp),
                fontFamily = Poppins,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Image(painter = painterResource(id = R.drawable.right_arrow), colorFilter = ColorFilter.tint(
                Color.DarkGray), contentDescription = null)
        }


    }
}


@Preview(showSystemUi = true)
@Composable
private fun NewsDetailsContentPreview() {
    NewsDetailsContent(
        article = Article(
            title = "Why are football's biggest clubs starting a new \n" + "tournament?",
            source = Source(name = "ABC News", id = "1"),
            publishedAt = "3 hours ago",
            content = "dsmnbnm,mnbvbnmnb"
        ), modifier = Modifier)


}


