package com.example.newsflow.features.news_feature.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsflow.common.ui_interface.UiEffect
import com.example.newsflow.common.ui_interface.UiEvent
import com.example.newsflow.common.ui_interface.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class NewsBaseViewModel<State : UiState, Effect : UiEffect> : ViewModel() {

    abstract val initialState: State

    private val _uiState: MutableStateFlow<State> by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<State>
        get() = _uiState.asStateFlow()

    fun setState(reduce: State.() -> State) {
        _uiState.value = uiState.value.reduce()
    }

    private val _uiEffect: Channel<Effect> = Channel()
    val uiEffect: Flow<Effect>
        get() = _uiEffect.receiveAsFlow()

    fun setEffect(builder: () -> Effect) {
        val effect = builder()
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }
}