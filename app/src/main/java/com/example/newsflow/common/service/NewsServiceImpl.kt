package com.example.newsflow.common.service

import com.example.newsflow.common.api.NewsAPI
import com.example.newsflow.common.models.NewsResponse
import javax.inject.Inject

class NewsServiceImpl @Inject constructor(private val newsAPI: NewsAPI) : NewsService {
    override suspend fun getAllNews(
        country: String,
        category: String,
        apiKey: String,
        page: Int,
        pageSize: Int
    ): NewsResponse {
        return newsAPI.getAllNews(country, category, apiKey, page, pageSize)
    }
}