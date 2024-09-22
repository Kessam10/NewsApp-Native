package com.example.news_compose_c40.screens.news_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_compose_c40.R
import com.example.news_compose_c40.model.article.Article
import com.example.news_compose_c40.model.article.ArticlesResponse
import com.example.news_compose_c40.model.source.SourcesResponse
import com.example.news_compose_c40.util.UIMessage
import com.example.news_compose_c40.util.fromJson
import com.route.newsappc40gsat.api.NewsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(val newsService: NewsService,savedStateHandle: SavedStateHandle):ViewModel() {


    private val title:String? = savedStateHandle["title"]

    private val _article = mutableStateOf<Article?>(null)
    val article:Article? get() = _article.value

    private val _uiMessage = mutableStateOf(UIMessage())
    val uiMessage: UIMessage get() = _uiMessage.value

    private val _isErrorDialogVisible = mutableStateOf(true)
    val isErrorDialogVisible: Boolean get() = _isErrorDialogVisible.value

    fun hideErrorDialog() {
        _isErrorDialogVisible.value = false
    }
     fun getArticle(){

         viewModelScope.launch (Dispatchers.IO){
             try {
                 _uiMessage.value = UIMessage(isLoading = true)
                 if (title != null) {
                     val article = newsService.getArticle(title).articles?.get(0)
                     _article.value = article

                 }
                 _uiMessage.value = UIMessage(isLoading = false)
             } catch (e: HttpException) {
                 val articlesResponse = e.response()?.errorBody()?.string()?.fromJson(
                     ArticlesResponse::class.java
                 )
                 _uiMessage.value= UIMessage(
                     isLoading = false,
                     errorMessage = articlesResponse?.message,
                     retryAction = {
                         getArticle()
                     })


             } catch (e: UnknownHostException) {

                 _uiMessage.value = UIMessage(
                     isLoading = false,
                     errorMessageId = R.string.connection_error,
                     retryAction = {
                         getArticle()
                     })

             } catch (e: Exception) {
                 _uiMessage.value = UIMessage(
                     isLoading = false,
                     errorMessage = e.localizedMessage,
                     retryAction = {
                         getArticle()
                     })

             }


         }

    }
}


