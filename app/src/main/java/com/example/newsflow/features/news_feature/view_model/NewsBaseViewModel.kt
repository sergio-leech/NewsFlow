package com.example.newsflow.features.news_feature.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsflow.common.ui_interface.UiEffect
import com.example.newsflow.common.ui_interface.UiEvent
import com.example.newsflow.common.ui_interface.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class NewsBaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> :
    ViewModel() {

    abstract val initialState: State

    private val _uiState: MutableStateFlow<State> by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<State>
        get() = _uiState.asStateFlow()

    fun setState(reduce: State.() -> State) {
        _uiState.value = uiState.value.reduce()
    }

    private val _uiEvent: MutableSharedFlow<Event> = MutableSharedFlow()
    val uiEvent: SharedFlow<Event>
        get() = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            uiEvent.collect { event ->
                eventHandler(event)
            }
        }
    }

    abstract fun eventHandler(event: Event)

    fun setEvent(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
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