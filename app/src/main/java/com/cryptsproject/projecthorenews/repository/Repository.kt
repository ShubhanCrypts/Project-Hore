package com.cryptsproject.projecthorenews.repository

import com.cryptsproject.projecthorenews.api.RetrofitInstance

class Repository {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlinesNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)


}