package com.example.newsflow.features.news_feature.view_model

import androidx.paging.PagingData
import com.example.newsflow.common.models.Article
import com.example.newsflow.common.ui_interface.UiEffect
import com.example.newsflow.common.ui_interface.UiEvent
import com.example.newsflow.common.ui_interface.UiState
import kotlinx.coroutines.flow.Flow

class NewsContract {

    data class State(
        val newsState: NewsState
    ) : UiState

    sealed class NewsState {
        object Idle : NewsState()
        object Loading : NewsState()
        data class Success(val news: PagingData<Article>):NewsState()
        data class Error(val exception: String):NewsState()
    }

    sealed class Event : UiEvent {
        object OnClickNews : Event()
    }

    sealed class Effect : UiEffect {
        object OnShowToast : Effect()
    }
}