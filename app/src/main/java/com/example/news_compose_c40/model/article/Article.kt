package com.example.news_compose_c40.model.article

import com.example.news_compose_c40.model.source.Source
import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class Article(

	@field:SerializedName("publishedAt")
	var publishedAt: String? = null,

	@field:SerializedName("author")
	var author: String? = null,

	@field:SerializedName("urlToImage")
	var urlToImage: String? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@field:SerializedName("source")
	var source: Source? = null,

	var sourceId: String? = null,


	@field:SerializedName("title")
	var title: String?=null,

	@field:SerializedName("url")
	var url: String? = null,

	@field:SerializedName("content")
	var content: String? = null,
)
