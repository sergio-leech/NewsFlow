package com.example.newsflow.features.detail_news_feature

import com.example.newsflow.common.models.Article
import com.example.newsflow.common.ui_interface.UiEffect
import com.example.newsflow.common.ui_interface.UiEvent
import com.example.newsflow.common.ui_interface.UiState

class DetailNewsContract {


    data class State(
        val newsState:NewsState
    ):UiState
    sealed class NewsState{
        object Idle:NewsState()
        object Loading:NewsState()
        data class Success(val article: Article):NewsState()
        data class Error(val exception:String):NewsState()
    }

    sealed class Event:UiEvent{

    }

    sealed class Effect:UiEffect{

    }
}