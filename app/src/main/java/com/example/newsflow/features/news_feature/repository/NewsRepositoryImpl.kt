package com.example.newsflow.features.news_feature.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsflow.common.Constant.NETWORK_PAGE_SIZE
import com.example.newsflow.common.models.Article
import com.example.newsflow.common.paging.NewsPagingSource
import com.example.newsflow.common.service.NewsService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsService: NewsService):NewsRepository {
    override fun getNewsList(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(newsService) }
        ).flow
    }
}