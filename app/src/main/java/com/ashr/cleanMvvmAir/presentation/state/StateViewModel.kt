package com.ashr.cleanMvvmAir.presentation.state

import androidx.lifecycle.viewModelScope
import com.ashr.cleanMvvmAir.core.dispatcher.DispatcherAnnotations
import com.ashr.cleanMvvmAir.domain.model.DomainState
import com.ashr.cleanMvvmAir.domain.usecase.GetStatesUseCase
import com.ashr.cleanMvvmAir.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StateViewModel @Inject constructor(
    private val getStatesUseCase: GetStatesUseCase,
    @DispatcherAnnotations.Io private val dispatcher: CoroutineDispatcher
) :
    BaseViewModel<StateListUiState, StateUiIntent>(StateListUiState()) {


    override fun processIntents(intent: StateUiIntent) {
        when (intent) {
            is StateUiIntent.GetStates -> getStates(countryName = intent.countryName)
            is StateUiIntent.ChangeFilter -> changeFilter(intent.filter)
        }
    }

    private fun changeFilter(filter: String) {
        updateState {
            it.copy(filter = filter, filteredStates = getFilteredStates(filter, it.fetchedStates))
        }
    }

    private fun getFilteredStates(
        filter: String,
        fetchedStates: List<DomainState>
    ): List<DomainState> {
        return fetchedStates.filter { state -> state.name.contains(filter, ignoreCase = true) }
    }

    private fun getStates(countryName: String) {
        viewModelScope.launch(dispatcher) {
            try {
                updateState {
                    it.copy(
                        isLoading = true,
                    )
                }
                val states = getStatesUseCase(countryName)
                updateState {
                    it.copy(
                        isLoading = false,
                        fetchedStates = states,
                        filteredStates = states,
                    )
                }
            } catch (_: Exception) {
                updateState {
                    it.copy(isLoading = false, error = "some error occurred")
                }
            }
        }
    }
}

data class StateListUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val fetchedStates: List<DomainState> = emptyList(),
    val filteredStates: List<DomainState> = emptyList(),
    val filter: String = ""
)

sealed class StateUiIntent {
    data class GetStates(val countryName: String) : StateUiIntent()
    data class ChangeFilter(val filter: String) : StateUiIntent()
}