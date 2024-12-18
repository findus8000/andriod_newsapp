package com.example.newsapp.model

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

//87c2cab3168a4780a835da1981a7b3ca
class News {

    private val gson = Gson()

    interface WeatherJsonPlaceholder {
        @GET("v2/top-headlines?country=us&apiKey=87c2cab3168a4780a835da1981a7b3ca")
        suspend fun getNewsData(): NewsResponse
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
        val title:String,
        val description:String,
        val url:String
    )


    suspend fun getNetNewsData(): NewsResponse {
        println("Getting news!")
        val jsonPlaceholderService = RetrofitClient.retrofit.create(WeatherJsonPlaceholder::class.java)

            try {
                val response = jsonPlaceholderService.getNewsData()
                println(response)
               return response
            } catch (e: Exception) {
                println("Error: ${e.message}")
                e.printStackTrace()
            }


        return NewsResponse(emptyList())
    }

}