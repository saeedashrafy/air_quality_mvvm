package com.ashr.cleanMvvmAir.presentation.city.data

import androidx.lifecycle.viewModelScope
import com.ashr.cleanMvvmAir.core.dispatcher.DispatcherAnnotations
import com.ashr.cleanMvvmAir.domain.model.DomainCityData
import com.ashr.cleanMvvmAir.domain.usecase.GetCityDataUseCase
import com.ashr.cleanMvvmAir.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CityDataViewModel @Inject constructor(
    private val getCityDataUseCase: GetCityDataUseCase,
    @DispatcherAnnotations.Io private val dispatcher: CoroutineDispatcher
) : BaseViewModel<CityDataUiState, CityDataUiIntent>(CityDataUiState(isLoading = true)) {

    override fun processIntents(intent: CityDataUiIntent) {
        when (intent) {
            is CityDataUiIntent.GetData -> getCityData(
                intent.countryName,
                intent.stateName,
                intent.cityName
            )
        }
    }

    private fun getCityData(countryName: String, stateName: String, cityName: String) {
        viewModelScope.launch(dispatcher) {
            try {
                val cityData = getCityDataUseCase(countryName, stateName, cityName)
                updateState {
                    it.copy(isLoading = false, data = cityData)
                }
            } catch (exp: Exception) {
                updateState {
                    it.copy(isLoading = false, error = "some error occurred")
                }
            }
        }
    }
}

data class CityDataUiState(
    val isLoading: Boolean = false,
    val data: DomainCityData? = null,
    val error: String = ""
)

sealed class CityDataUiIntent {
    data class GetData(val countryName: String, val stateName: String, val cityName: String) :
        CityDataUiIntent()
}