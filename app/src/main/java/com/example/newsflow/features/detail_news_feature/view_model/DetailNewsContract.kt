package com.example.newsflow.features.detail_news_feature.view_model


import com.example.newsflow.common.ui_interface.UiState

class DetailNewsContract {

    data class State(
        val newsState: NewsState
    ) : UiState

    sealed class NewsState {
        object Idle : NewsState()
        object Loading : NewsState()
        data class Success(val url: String) : NewsState()
        data class Error(val exception: String) : NewsState()
    }
}