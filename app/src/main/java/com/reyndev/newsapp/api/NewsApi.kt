package com.reyndev.newsapp.api

import com.reyndev.newsapp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "6c27bec32e074a1e8f4194873893051f"

interface NewsApi {
    @GET("/v2/everything")

    suspend fun getNews(
        @Query("q") q: String,
        @Query("language") lang: String,
        @Query("apiKey") key: String = API_KEY
    ): Response<NewsResponse>
}