package com.example.newsflow.features.detail_news_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsflow.common.ui_interface.UiEffect
import com.example.newsflow.common.ui_interface.UiEvent
import com.example.newsflow.common.ui_interface.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class DetailNewsBaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> :
    ViewModel() {

    abstract val initialState: State
    private val _uiSate: MutableStateFlow<State> by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<State>
        get() = _uiSate.asStateFlow()

    fun setState(reduce: State.() -> State) {
        _uiSate.value = uiState.value.reduce()
    }

    private val _uiEvent: MutableSharedFlow<Event> = MutableSharedFlow()
    val uiEvent: SharedFlow<Event> get() = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            uiEvent.collect { event ->
                handlerEvent(event)
            }
        }
    }

    abstract fun handlerEvent(event: Event)

    fun setEvent(event: Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private val _uiEffect: Channel<Effect> = Channel()
    val uiEffect: Flow<Effect> get() = _uiEffect.receiveAsFlow()

    fun setEffect(builder: () -> Effect) {
        val effect = builder()
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }
}