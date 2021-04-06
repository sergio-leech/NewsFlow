package com.example.newsflow.features.news_feature.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsflow.common.models.Article
import com.example.newsflow.features.news_feature.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    NewsBaseViewModel<NewsContract.State, NewsContract.Event, NewsContract.Effect>() {

    override val initialState: NewsContract.State
        get() = NewsContract.State(NewsContract.NewsState.Idle)

    override fun eventHandler(event: NewsContract.Event) {
        TODO("Not yet implemented")
    }

    init {
        renderNewsScreen()
    }

    private fun renderNewsScreen(){
        viewModelScope.launch {
            setState { copy(newsState = NewsContract.NewsState.Loading) }
            try {
                val data = newsRepository.getNewsList().cachedIn(viewModelScope)
                data.collect { list->
                    setState { copy(newsState = NewsContract.NewsState.Success(list)) }
                }
            }catch (exception:Exception){
                setState { copy(newsState = NewsContract.NewsState.Error(exception = exception.toString())) }
                //setEffect { NewsContract.Effect.OnShowToast }
            }
        }
    }
}