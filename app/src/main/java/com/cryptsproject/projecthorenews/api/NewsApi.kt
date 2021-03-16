package com.cryptsproject.projecthorenews.api

import com.cryptsproject.projecthorenews.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getHeadlinesNews(
        @Query("country")
        countryCode : String = "us",
        @Query("page")
        pageNumber : Int = 1,
        @Query("apiKey")
        apiKey : String = "4168c466b08a415a92a3ccd1ff51b42a"
    ): Response<NewsResponse>

}