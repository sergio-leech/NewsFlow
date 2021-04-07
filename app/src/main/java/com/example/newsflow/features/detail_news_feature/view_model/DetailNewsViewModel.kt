package com.example.newsflow.features.detail_news_feature.view_model

import androidx.lifecycle.SavedStateHandle
import com.example.newsflow.common.util.NavigationKeyArgsConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailNewsViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    DetailNewsBaseViewModel<DetailNewsContract.State>() {

    val article = savedStateHandle.get<String>(NavigationKeyArgsConstant.ARTICLE)

    override val initialState: DetailNewsContract.State
        get() = DetailNewsContract.State(DetailNewsContract.NewsState.Idle)

    init {
        renderDetailNewsScreen()
    }

    private fun renderDetailNewsScreen() {
        setState { copy(newsState = DetailNewsContract.NewsState.Loading) }
        try {
            article?.let { news ->
                setState { copy(newsState = DetailNewsContract.NewsState.Success(news)) }
            }
        } catch (e: Exception) {
            setState { copy(newsState = DetailNewsContract.NewsState.Error(e.toString())) }
        }
    }
}