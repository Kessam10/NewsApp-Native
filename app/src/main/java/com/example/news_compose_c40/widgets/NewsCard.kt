package com.example.news_compose_c40.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.news_compose_c40.R
import com.example.news_compose_c40.model.article.Article
import com.example.news_compose_c40.model.source.Source
import com.example.news_compose_c40.ui.theme.Poppins
import com.example.news_compose_c40.ui.theme.gray
import com.example.news_compose_c40.ui.theme.lightGray


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(article: Article, onNewsClick:((String,String)->Unit)?=null) {
    Column(modifier = Modifier.padding(8.dp).clickable {
        if (onNewsClick != null) {
            onNewsClick(article.title?:"",article.source?.name ?:"")
        }
    }) {

        GlideImage(
            model = article.urlToImage?:"",
            contentDescription = stringResource(R.string.news_image),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            loading =placeholder(R.drawable.loading),
            transition = CrossFade
        )
        Text(
            text = article.source?.name ?: "",
            fontWeight = FontWeight.Thin,
            fontSize = 10.sp,
            modifier = Modifier.padding(top = 8.dp),
            fontFamily = Poppins,
            color = gray
        )
        Text(
            text = article.title ?: "",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            modifier = Modifier.padding(top = 5.dp),
            fontFamily = Poppins,
            lineHeight = TextUnit(
                20f, TextUnitType.Sp
            )
        )
        Row(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                article.publishedAt ?: "",
                color = lightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewNewsItem() {
    NewsCard(
        Article(
            title = "Why are football's biggest clubs starting a new \n" + "tournament?",
            source = Source(name = "ABC News",id="1"),
            publishedAt = "3 hours ago"
        ),
    ){_,_->

    }
}
