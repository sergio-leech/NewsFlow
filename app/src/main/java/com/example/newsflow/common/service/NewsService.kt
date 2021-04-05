package com.example.newsflow.common.service

import com.example.newsflow.common.models.NewsResponse


interface NewsService {
    suspend fun getAllNews(
        country: String,
        category: String,
        apiKey: String,
        page: Int,
        pageSize: Int
    ):NewsResponse
}