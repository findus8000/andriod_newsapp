package com.example.newsapp.model

import android.app.Application

class NewsOdds(private val application: Application) {
    private val news = News()

     suspend fun getNews(){
         news.getNetNewsData()
     }
}