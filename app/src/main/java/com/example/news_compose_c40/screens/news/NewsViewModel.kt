package com.example.news_compose_c40.screens.news

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_compose_c40.R
import com.example.news_compose_c40.model.article.Article
import com.example.news_compose_c40.model.source.Source
import com.example.news_compose_c40.model.source.SourcesResponse
import com.example.news_compose_c40.util.UIMessage
import com.example.news_compose_c40.util.fromJson
import com.route.newsappc40gsat.api.NewsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsService: NewsService
) : ViewModel() {
    private val _sourcesList = mutableStateOf<List<Source>?>(null)
    val sourcesList: List<Source>? get() = _sourcesList.value

    private var _articlesList = mutableStateOf<List<Article>?>(null)
    val articlesList: List<Article>? get() = _articlesList.value
    private var fullArticlesList: List<Article>? = null

    private val _uiMessage = mutableStateOf(UIMessage())
    val uiMessage: UIMessage get() = _uiMessage.value

    private val _isErrorDialogVisible = mutableStateOf(true)
    val isErrorDialogVisible: Boolean get() = _isErrorDialogVisible.value


    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> get() = _searchQuery // Expose as State<String>



    fun showErrorDialog() {
        _isErrorDialogVisible.value = true
    }

    fun hideErrorDialog() {
        _isErrorDialogVisible.value = false
    }

    fun getSources(categoryId: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiMessage.value = UIMessage(isLoading = true)
                val sources = newsService.getNewsSources(categoryId = categoryId).sources
                _uiMessage.value = UIMessage(isLoading = false)

                if (sources != null) {
                    _sourcesList.value = sources
                }


            } catch (e: HttpException) {
                val sourcesResponse = e.response()?.errorBody()?.string()?.fromJson(
                    SourcesResponse::class.java
                )
                _uiMessage.value = UIMessage(
                    isLoading = false,
                    errorMessage = sourcesResponse?.message,
                    retryAction = {
                        getSources(categoryId)
                    })


            } catch (e: UnknownHostException) {

                _uiMessage.value = UIMessage(
                    isLoading = false,
                    errorMessageId = R.string.connection_error,
                    retryAction = {
                        getSources(categoryId)
                    })

            } catch (e: Exception) {
                _uiMessage.value = UIMessage(
                    isLoading = false,
                    errorMessage = e.localizedMessage,
                    retryAction = {
                        getSources(categoryId)
                    })

            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    fun getNewsBySource(sourceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiMessage.value = UIMessage(isLoading = true)
                val articles = newsService.getNewsBySource(sourceId = sourceId).articles
                _uiMessage.value = UIMessage(isLoading = false)

                if (!articles.isNullOrEmpty()) {
                    _articlesList.value = articles
                    fullArticlesList = articles
                } else
                _uiMessage.value = UIMessage(shouldDisplayNoArticlesFound = true)

            } catch (e: HttpException) {
                val sourcesResponse = e.response()?.errorBody()?.string()?.fromJson(
                    SourcesResponse::class.java
                )
                _uiMessage.value = UIMessage(
                    isLoading = false,
                    errorMessage = sourcesResponse?.message,
                    retryAction = {
                        getNewsBySource(sourceId)
                    })


            } catch (e: UnknownHostException) {

                _uiMessage.value = UIMessage(
                    isLoading = false,
                    errorMessageId = R.string.connection_error,
                    retryAction = {
                        getNewsBySource(sourceId)
                    })

            } catch (e: Exception) {
                _uiMessage.value = UIMessage(
                    isLoading = false,
                    errorMessage = e.localizedMessage,
                    retryAction = {
                        getNewsBySource(sourceId)
                    })

            }
        }

    }
    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        filterArticles(newQuery)
    }

    private fun filterArticles(query: String) {
        if (query.isEmpty()) {
            _articlesList.value = fullArticlesList
        } else {
            _articlesList.value = fullArticlesList?.filter {
                it.title?.contains(query, ignoreCase = true) == true
            }
        }
    }
}
