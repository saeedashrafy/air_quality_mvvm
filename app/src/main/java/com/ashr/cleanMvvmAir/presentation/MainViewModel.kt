package com.ashr.cleanMvvmAir.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() :
    BaseViewModel<MainUiState, MainUiIntent>(MainUiState()) {

    override fun processIntents(intent: MainUiIntent) {
        when (intent) {
            is MainUiIntent.ChangeTheme -> {
                changeTheme()
            }
        }
    }

    private fun changeTheme() {
        updateState {
            it.copy(themIsDark = !it.themIsDark)
        }
    }

}

data class MainUiState(
    val themIsDark: Boolean = false
)

sealed class MainUiIntent {
    object ChangeTheme : MainUiIntent()
}