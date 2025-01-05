package com.example.newsapp.model

import androidx.compose.foundation.pager.PageSize
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class News {

    interface NewsJsonPlaceholder {
        @GET("v2/top-headlines")
        suspend fun getNewsData(
            @Query("language") language: String = "en",
            @Query("apiKey") apiKey: String = "87c2cab3168a4780a835da1981a7b3ca"
        ): NewsResponse

        @GET("v2/everything")
        suspend fun getNewsDataSearch(
            @Query("q") q: String = "",
            @Query("language") language: String = "en",
            @Query("apiKey") apiKey: String = "87c2cab3168a4780a835da1981a7b3ca",
            @Query("pageSize") pageSize: Int = 20,
            @Query("sortBy") sortBy:String = "relevancy"
        ): NewsResponse
    }

    object RetrofitClient {
        private const val BASE_URL = "https://newsapi.org/"

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    data class NewsResponse(
        val articles: List<Articles>
    )

    data class Articles(
        var title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val source:Source
    )

    data class Source(
        val id:String,
        val name:String
    )

    suspend fun getBasicNewsData(): NewsResponse {
        val jsonPlaceholderService =
            RetrofitClient.retrofit.create(NewsJsonPlaceholder::class.java)
        try {
            val response = jsonPlaceholderService.getNewsData()
            return response
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
        }
        return NewsResponse(emptyList())
    }

    suspend fun searchNewsData(string:String): NewsResponse {
        val jsonPlaceholderService =
            RetrofitClient.retrofit.create(NewsJsonPlaceholder::class.java)
        try {
            val response = jsonPlaceholderService.getNewsDataSearch(q = string)
            return response
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
        }
        return NewsResponse(emptyList())
    }

}