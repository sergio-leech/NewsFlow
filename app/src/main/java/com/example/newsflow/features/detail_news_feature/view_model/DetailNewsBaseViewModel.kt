package com.example.newsflow.features.detail_news_feature.view_model

import androidx.lifecycle.ViewModel
import com.example.newsflow.common.ui_interface.UiState
import kotlinx.coroutines.flow.*

abstract class DetailNewsBaseViewModel<State : UiState> : ViewModel() {

    abstract val initialState: State
    private val _uiSate: MutableStateFlow<State> by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<State>
        get() = _uiSate.asStateFlow()

    fun setState(reduce: State.() -> State) {
        _uiSate.value = uiState.value.reduce()
    }
}