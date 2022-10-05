package com.ashr.cleanMvvmAir.presentation

import com.ashr.cleanMvvmAir.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() :
    BaseViewModel<MainUiState, MainUiIntent>(MainUiState()) {

    init {
        Timber.d("Shared")
    }

    override fun processIntents(intent: MainUiIntent) {
        when (intent) {
            is MainUiIntent.ChangeTitle -> {
                changeTitle(intent.title)
            }
        }
    }

    private fun changeTitle(title: String) {
        updateState {
            it.copy(toolbarTitle = title)
        }
    }
}

data class MainUiState(
    val toolbarTitle: String = ""
)

sealed class MainUiIntent {
    data class ChangeTitle(val title: String) : MainUiIntent()
}