package com.example.news_compose_c40.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.news_compose_c40.model.article.Article
import com.example.news_compose_c40.model.source.Source

@Composable
fun NewsList(newsList: List<Article>?, shouldDisplayNoArticlesFound: Boolean, loadingState: Boolean, onNewsClick:(String,String)->Unit) {

        if (!shouldDisplayNoArticlesFound) {
            Box {
                LazyColumn(verticalArrangement = Arrangement.SpaceEvenly) {
                    newsList?.let {
                        items(newsList) { news->
                            NewsCard(news) { title,sourceName ->
                                onNewsClick(title,sourceName)
                            }
                        }
                    }

                }
                ProgressIndicator(loadingState)
            }
        } else {
            ArticlesNotFound()
        }

}



@Preview(showSystemUi = true)
@Composable
private fun PreviewNewsList() {
    NewsList(listOf(Article(
        title = "Why are football's biggest clubs starting a new \n" + "tournament?",
        source = Source(name = "ABC News",id="1"),
        publishedAt = "3 hours ago"
    ),Article(
        title = "Why are football's biggest clubs starting a new \n" + "tournament?",
        source = Source(name = "ABC News",id="1"),
        publishedAt = "3 hours ago"
    )), false,false, onNewsClick ={_,_->

    })
}
