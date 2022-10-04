package com.ashr.cleanMvvmAir.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State, Intent>(
    initialState: State
) : ViewModel() {

    protected val _uiState = MutableStateFlow<State>(initialState)
    val uiState = _uiState.asStateFlow()


    protected fun updateState(reduce: (oldState: State) -> State) {
        _uiState.update { oldState ->
            reduce(oldState)
        }
    }

    abstract fun processIntents(intent: Intent)

}