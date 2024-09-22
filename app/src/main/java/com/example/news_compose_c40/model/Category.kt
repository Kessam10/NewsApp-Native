package com.example.news_compose_c40.model

import com.example.news_compose_c40.R


data class Category( val name:Int,val image:Int,val apiID:String)

val categoriesList =
    listOf(
        Category(R.string.business, R.drawable.business,"business"),
        Category(R.string.entertainment, R.drawable.entertainment,"entertainment"),
        Category(R.string.health, R.drawable.health,"health"),
        Category(R.string.science, R.drawable.science,"science"),
        Category(R.string.sports, R.drawable.sports,"sports"),
        Category(R.string.technology, R.drawable.technology,"technology")
    )
