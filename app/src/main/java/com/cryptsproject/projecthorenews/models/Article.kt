package com.cryptsproject.projecthorenews.models

import java.io.Serializable

data class Article(
    val id: String?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) :Serializable