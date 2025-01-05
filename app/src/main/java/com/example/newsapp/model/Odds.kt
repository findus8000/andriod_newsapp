package com.example.newsapp.model


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class Odds {

    interface OddsJsonPlaceholder {
        @GET("markets")
        suspend fun getOddsData(
            @Query("limit") limit: Int = 1000,
            @Query("start_date_min") startDate: String = "2024-09-01T00:00:00Z",
            @Query("start_date_max") endDate:String = OffsetDateTime.now().format(DateTimeFormatter.ISO_INSTANT),
            @Query("closed") closed: Boolean = false,
            @Query("volume_num_min") minVolume: Int = 100000
        ): List<Market>
    }

    object RetrofitClient {
        private const val BASE_URL = "https://gamma-api.polymarket.com/"

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    data class Market(
        val question:String,
        val description:String,
        val outcomes:String,
        val outcomePrices:String,
        val volume:String
    )

    suspend fun getNetOddsData(startDate: String, endDate: String, volume: Int):  List<Market> {
        val jsonPlaceholderService = RetrofitClient.retrofit.create(OddsJsonPlaceholder::class.java)
        try {
            val response = jsonPlaceholderService.getOddsData(startDate = startDate, endDate = endDate, minVolume = volume)
            return response
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
        }


        return emptyList()
    }

}