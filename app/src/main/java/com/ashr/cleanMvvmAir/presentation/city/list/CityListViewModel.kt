package com.ashr.cleanMvvmAir.presentation.city.list

import androidx.lifecycle.viewModelScope
import com.ashr.cleanMvvmAir.core.dispatcher.DispatcherAnnotations
import com.ashr.cleanMvvmAir.domain.model.DomainCity
import com.ashr.cleanMvvmAir.domain.usecase.GetCityUseCase
import com.ashr.cleanMvvmAir.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val getCityUseCase: GetCityUseCase,
    @DispatcherAnnotations.Io private val dispatcher: CoroutineDispatcher
) : BaseViewModel<CityUiState, CityUiIntent>(CityUiState()) {
    override fun processIntents(intent: CityUiIntent) {
        when (intent) {
            is CityUiIntent.GetCities -> getCities(intent.countryName, intent.stateName)
            is CityUiIntent.ChangeFilter -> changeFilter(intent.filter)
            else -> {}
        }
    }

    private fun getCities(countryName: String, stateName: String) {
        viewModelScope.launch(dispatcher) {
            try {
                updateState {
                    it.copy(
                        isLoading = true,
                    )
                }
                val cities = getCityUseCase(countryName, stateName)
                updateState {
                    it.copy(
                        isLoading = false,
                        fetchedCities = cities,
                        filteredCities = cities,
                        filter = ""
                    )
                }
            } catch (_: Exception) {
                updateState {
                    it.copy(isLoading = false, error = "some error occurred")
                }
            }
        }
    }

    private fun changeFilter(filter: String) {
        updateState {
            it.copy(filter = filter, filteredCities = getFilteredCities(filter, it.fetchedCities))
        }
    }

    private fun getFilteredCities(
        filter: String,
        fetchedCities: List<DomainCity>
    ): List<DomainCity> {
        return fetchedCities.filter { city -> city.name.contains(filter, ignoreCase = true) }
    }


}


data class CityUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val fetchedCities: List<DomainCity> = emptyList(),
    val filteredCities: List<DomainCity> = emptyList(),
    val filter: String = ""
)

sealed class CityUiIntent {
    data class GetCities(val countryName: String, val stateName: String) : CityUiIntent()
    data class ChangeFilter(val filter: String) : CityUiIntent()
}