package com.example.newsflow.common.service

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsServiceModule {

    @Provides
    @Singleton
    fun provideNewsRepository(newsRepositoryImpl: NewsServiceImpl): NewsService {
        return newsRepositoryImpl
    }
}