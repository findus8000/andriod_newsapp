package com.example.newsapp

import android.app.Application
import com.example.newsapp.model.NewsOdds

class NewsApplication: Application() {
    lateinit var newsOdds:NewsOdds


    override fun onCreate() {
        super.onCreate()
        newsOdds = NewsOdds(this)
    }
}