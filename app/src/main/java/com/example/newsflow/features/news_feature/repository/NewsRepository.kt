package com.example.newsflow.features.news_feature.repository

import androidx.paging.PagingData
import com.example.newsflow.common.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsList(): Flow<PagingData<Article>>
}