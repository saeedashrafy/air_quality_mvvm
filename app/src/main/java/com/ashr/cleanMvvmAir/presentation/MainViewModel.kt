package com.ashr.cleanMvvmAir.presentation

import com.ashr.cleanMvvmAir.presentation.base.BaseViewModel

class MainViewModel : BaseViewModel<MainUiState, MainUiIntent>(MainUiState()) {

    override fun processIntents(intent: MainUiIntent) {
        TODO("Not yet implemented")
    }
}

data class MainUiState(
    val toolbarTitle: String = ""
)

sealed class MainUiIntent {
    data class ChangeTitle(val title: String) : MainUiIntent()
}