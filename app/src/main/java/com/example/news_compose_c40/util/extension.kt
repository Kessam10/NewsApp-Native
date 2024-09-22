package com.example.news_compose_c40.util

import com.google.gson.Gson


fun <T>String.fromJson(className:Class<T>):T{
    return Gson().fromJson(this,className)

}
