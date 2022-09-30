package com.ashr.cleanMvvmAir.presentation.country

import androidx.lifecycle.viewModelScope
import com.ashr.cleanMvvmAir.core.dispatcher.DispatcherAnnotations
import com.ashr.cleanMvvmAir.domain.model.DomainCountry
import com.ashr.cleanMvvmAir.domain.usecase.GetCountriesUseCase
import com.ashr.cleanMvvmAir.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val getAllCountriesUseCase: GetCountriesUseCase,
    @DispatcherAnnotations.Io private val dispatcher: CoroutineDispatcher,
) : BaseViewModel<CountryUiState, CountryUiIntent>(CountryUiState()) {


    override fun processIntents(intent: CountryUiIntent) {
        when (intent) {
            is CountryUiIntent.GetCountries -> {
                getCountries()
            }
            is CountryUiIntent.ChangeFilter -> {
                changeFilter(intent.filter)
            }
        }
    }


    private fun getCountries() {
        viewModelScope.launch(dispatcher) {
            try {
                updateState {
                    it.copy(
                        isLoading = true,
                    )
                }
                val countries = getAllCountriesUseCase()
                updateState {
                    it.copy(
                        isLoading = false,
                        fetchedCountries = countries,
                        filteredCountries = countries,
                    )
                }
            } catch (exp: Exception) {
                updateState {
                    it.copy(isLoading = false, error = "some error occurred")
                }
            }
        }
    }

    private fun changeFilter(filter: String) {
        updateState {
            it.copy(
                filter = filter,
                filteredCountries = getFilteredCountries(filter, it.fetchedCountries)
            )
        }
    }

    private fun getFilteredCountries(
        filter: String,
        fetchedCountries: List<DomainCountry>
    ): List<DomainCountry> {
        return fetchedCountries.filter { country ->
            country.name.contains(
                filter,
                ignoreCase = true
            )
        }
    }


}


data class CountryUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val fetchedCountries: List<DomainCountry> = emptyList(),
    val filteredCountries: List<DomainCountry> = emptyList(),
    val filter: String = ""
)

sealed class CountryUiIntent {
    object GetCountries : CountryUiIntent()
    data class ChangeFilter(val filter: String) : CountryUiIntent()
}