package com.route.newsappc40gsat.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthAPIKeyInterceptor(private val apiKey: String) :
    Interceptor {
    private val apiQueryKey = "apiKey"
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val urlBuilder = chain.request().url.newBuilder()
        urlBuilder.addQueryParameter(apiQueryKey, apiKey)
        requestBuilder.url(urlBuilder.build())
        return chain.proceed(request = requestBuilder.build())
    }
}
