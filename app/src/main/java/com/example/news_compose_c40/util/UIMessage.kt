package com.example.news_compose_c40.util

data class UIMessage(
    var isLoading:Boolean = false,
    var retryAction:(()->Unit)? = null,
    var errorMessage:String?=null,
    var errorMessageId:Int?=null,
    var shouldDisplayNoArticlesFound:Boolean = false
)
