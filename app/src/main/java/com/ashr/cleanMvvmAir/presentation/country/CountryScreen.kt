package com.ashr.cleanMvvmAir.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashr.cleanMvvmAir.R
import com.ashr.cleanMvvmAir.presentation.country.CountryUiIntent
import com.ashr.cleanMvvmAir.presentation.country.CountryViewModel
import com.ashr.cleanMvvmAir.presentation.ui.widget.AppTextField
import com.ashr.cleanMvvmAir.presentation.ui.widget.CircularLoadingView

@Composable
fun CountryScreen(
    title: MutableState<String>,
    padding: PaddingValues,
    onNavigateToStates: (String) -> Unit
) {
    title.value = stringResource(id = R.string.app_name)
    val viewModel: CountryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (uiState.fetchedCountries.isEmpty())
            viewModel.processIntents(CountryUiIntent.GetCountries)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading)
            CircularLoadingView()
        else if (uiState.fetchedCountries.isEmpty()) {
            ErrorHandler {
                viewModel.processIntents(CountryUiIntent.GetCountries)
            }
        } else {
            AppTextField(value = uiState.filter,
                label = "filter",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onValueChange = { value ->
                    viewModel.processIntents(
                        CountryUiIntent.ChangeFilter(
                            value
                        )
                    )
                })

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(uiState.filteredCountries) { country ->
                    ItemRow(country.name, onClick = { onNavigateToStates(country.name) })
                }
            }
        }
    }


}